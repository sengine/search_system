package com.searchengine.bool.service;

import com.searchengine.bool.anootation.LoggingBefore;
import com.searchengine.bool.domain.*;
import com.searchengine.bool.repository.DataService;
import com.searchengine.bool.search.ISearcher;
import com.searchengine.bool.search.Searcher;
import com.searchengine.bool.util.ITerminator;
import com.searchengine.bool.util.ITokenizer;
import com.searchengine.bool.util.WordTerminator;
import com.searchengine.bool.util.WordTokenizer;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Service Layer for find satysfying
 * the search criteria
 *
 * Created by IntelliJ IDEA.
 * User: entrix
 * Date: 16.03.2012
 * Time: 23:13
 * To change this template use File | Settings | File Templates.
 */
public class SearchService {

    private static Logger logger = Logger.getLogger(SearchService.class);

    private static ISearcher   searcher;
    private static ITerminator terminator;
    private static ITokenizer  tokenizer;
    
    static {
        searcher   = new Searcher();
        terminator = new WordTerminator();
        tokenizer  = new WordTokenizer();
    }

    /**
     *
     *
     * @param tokens - vector of tokens
     * @param signs - vector of token signs
     * @return
     */
    public static List<IDocument> findDocuments(List<IToken> tokens, List<Boolean> signs) {
        List<Term<String>> terms = new ArrayList<Term<String>>();

        // get terms with respect to tokens
        for (IToken token : tokens) {
            terms.add((Term<String>) terminator.getTermRelatedToToken(token));
        }

        List<SearchParameter> searchParameters =
                new ArrayList<SearchParameter>(terms.size());
        Iterator<Boolean> it = signs.iterator();
        
        // construct the search parameters
        for (ITerm<String> term : terms) {
            searchParameters.add(
                    new SearchParameter(term, it.next()));
        }

        searcher.prepareSearch(searchParameters);
        return getDocuments(searcher.performSearch());
    }


    public static boolean addDocument(IDocument document) {
        List<ITerm> terms = new ArrayList<ITerm>();
        List<IToken> tokens = tokenizer.getTokensFromDocument(document);

        // get terms with respect to tokens
        for (IToken token : tokens) {
            terms.add(terminator.getTermRelatedToToken(token));
        }

        searcher.addTerms(terms, document.getDocId());

        return true;
    }

    public static boolean addDocuments(List<Document> documents) {
        for (Document doc : documents) {
            addDocument(doc);
        }
        return true;
    }

    public static List<IDocument> getDocuments(List<Long> docIdList) {
        List<IDocument> documents = new ArrayList<IDocument>();
        for (IDocument document : DataService.loadDocuments(docIdList)) {
            documents.add(document);
        }
        return documents;
    }

    @LoggingBefore("start updateIndex")
    synchronized public static void updateIndex() {

//        Term<String> termOne   = new Term<String>();
//        termOne.setValue("ololo");
//        DataService.saveTerm(termOne);
//        Long id = DataService.getTermId(termOne.getValue());

        try {
            String loadDirectory = "../webapps/sengine/WEB-INF/classes/load/";
            String[] commands = {"ls", loadDirectory};
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(commands);
            List<String> resultFiles = new ArrayList<String>();

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(proc.getInputStream()));

//            BufferedReader stdError = new BufferedReader(new
//                    InputStreamReader(proc.getErrorStream()));

            // read the output from the command
            String s;
            System.out.println("Here is the standard output of the command:\n");
                while ((s = stdInput.readLine()) != null) {
                    resultFiles.add(s);
                    System.out.println(s);
            }

//            // read any errors from the attempted command
//            System.out.println("Here is the standard error of the command (if any):\n");
//            while ((s = stdError.readLine()) != null) {
//                System.out.println(s);
//            }

            for (String fileName : resultFiles) {
                String content = "";
                String line    = "";
                BufferedReader reader = new BufferedReader(new FileReader(loadDirectory + fileName));

                reader.readLine();
                while(reader.ready()) {
                    line = reader.readLine();
                    while (line != null && !line.matches("^-+.*")) {
                        content += line + System.getProperty("line.separator");
                        line = reader.readLine();
                    }
                    addDocument(new Document(content));
                    content = "";
                }
                reader.close();
                Runtime.getRuntime().exec(new String[] { "rm", loadDirectory + fileName });
            }
        } catch (FileNotFoundException e) {
            logger.error("Error reading documents from file bash.txt", e.getCause());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            logger.error("Error reading documents from file bash.txt", e.getCause());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

//        DataService service =
//                (DataService) ApplicationServiceImpl.getBean("dataService");
//
//        // term test
//        Term<String> termOne   = new Term<String>();
//        Term<String> termTwo   = new Term<String>();
//        Term<String> termThree = new Term<String>();
//        termOne.setValue("aardvark");
//        termTwo.setValue("never");
//        termThree.setValue("dark");
//        DataService.saveTerm(termOne);
//        DataService.saveTerm(termTwo);
//        DataService.saveTerm(termThree);
//        System.out.println("termOne: " + DataService.loadTerm(termOne.getId()).getValue());
//        System.out.println("termTwo: " + DataService.loadTerm(termTwo.getId()).getValue());
//        System.out.println("termThree: " + DataService.loadTerm(termThree.getId()).getValue());
//
//        // document test
//        Document document = new Document("aardvark zero");
//        DataService.saveDocument(document);
//        document = new Document("aardvark one");
//        DataService.saveDocument(document);
//        document = new Document("aardvark two never");
//        DataService.saveDocument(document);
//        document = new Document("aardvark three never");
//        DataService.saveDocument(document);
//        document = new Document("never three");
//        DataService.saveDocument(document);
//        document = new Document("never four");
//        DataService.saveDocument(document);
//        document = new Document("never fife");
//        DataService.saveDocument(document);
//        document = new Document("never six");
//        DataService.saveDocument(document);
//        document = new Document("never seven dark");
//        DataService.saveDocument(document);
//        document = new Document("dark eight");
//        DataService.saveDocument(document);
//        document = new Document("dark nine");
//        DataService.saveDocument(document);
//        document = new Document("dark one");
//        DataService.saveDocument(document);
//
//        System.out.println("document: " + DataService.loadDocument(document.getDocId()).getContent());
//
//        // postings test
//        List<Integer> postings = new ArrayList<Integer>(10);
//        for (int i = 1; i < 4; ++i) {
//            postings.add(i);
//        }
//        DataService.savePostings(postings, termOne.getId());
//        postings.clear();
//        for (int i = 2; i < 8; ++i) {
//            postings.add(i);
//        }
//        DataService.savePostings(postings, termTwo.getId());
//        postings.clear();
//        for (int i = 7; i < 10; ++i) {
//            postings.add(i);
//        }
//        postings.add(1);
//        DataService.savePostings(postings, termThree.getId());
//        List<Term> terms = new ArrayList<Term>();
//        terms.add(termOne);
//        terms.add(termTwo);
//        terms.add(termThree);
//        int i = 0;
//        for (List<Integer> posts : DataService.loadPostings(terms)) {
//            System.out.println("term: " + terms.get(i).getValue());
//            for (Integer post : posts) {
//                System.out.println("    post: " + post);
//            }
//            i++;
//        }
//
//        List<ITerm> termsOne   = new ArrayList<ITerm>();
//        termsOne.add(termOne);
//        List<ITerm> termsTwo   = new ArrayList<ITerm>();
//        termsTwo.add(termTwo);
//        List<ITerm> termsThree = new ArrayList<ITerm>();
//        termsThree.add(termThree);
//        Indexator.getIndexator().addTerms(termsOne, 1L);
//        Indexator.getIndexator().addTerms(termsOne, 2L);
//        Indexator.getIndexator().addTerms(termsOne, 3L);
//        Indexator.getIndexator().addTerms(termsTwo, 2L);
//        Indexator.getIndexator().addTerms(termsTwo, 3L);
//        Indexator.getIndexator().addTerms(termsTwo, 4L);
//        Indexator.getIndexator().addTerms(termsTwo, 5L);
//        Indexator.getIndexator().addTerms(termsTwo, 6L);
//        Indexator.getIndexator().addTerms(termsTwo, 7L);
//        Indexator.getIndexator().addTerms(termsThree, 7L);
//        Indexator.getIndexator().addTerms(termsThree, 8L);
//        Indexator.getIndexator().addTerms(termsThree, 9L);
//        Indexator.getIndexator().addTerms(termsThree, 1L);

        // Delete all of them!
//        DataService.removePostings(termOne.getId());
//        DataService.removePostings(termTwo.getId());
//        DataService.removePostings(termThree.getId());
//        DataService.removeTerm(termOne.getId());
//        DataService.removeTerm(termTwo.getId());
//        DataService.removeTerm(termThree.getId());
//        DataService.removeDocument(document.getId());
//        DataService.removeIndex();
    }
}
