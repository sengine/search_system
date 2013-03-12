package com.searchengine.bool.repository;

import aj.org.objectweb.asm.Type;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.searchengine.bool.domain.Document;
import com.searchengine.bool.domain.ITerm;
import com.searchengine.bool.domain.Term;
import com.searchengine.bool.search.Indexator;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: entrix
 * Date: 16.03.2012
 * Time: 23:13
 * To change this template use File | Settings | File Templates.
 */
public class DataService {

    private static DataServiceImpl dataService;

    private void setDataSource(DataSource dataSource) {
        dataService = new DataService.DataServiceImpl();
        dataService.setDataSource(dataSource);
    }

    public void setDataSource(ComboPooledDataSource dataSource) {
        setDataSource((DataSource) dataSource);
    }

    @Transactional
    public static ITerm loadTerm(long id) {
        return dataService.loadTerm(id);
    }

    @Transactional
    public static boolean saveTerm(Term term) {
        return dataService.saveTerm(term);
    }


    @Transactional
    public static Long getTermId(String value) {
        return dataService.getTermId(value);
    }

    @Transactional
    public static boolean saveTerms(List<Term> term) {
        return dataService.saveTerms(term);

    }

    @Transactional
    public static boolean removeTerm(Long id) {
        return dataService.removeTerm(id);
    }

    @Transactional
    public static Document loadDocument(Long id) {
        return dataService.loadDocument(id);
    }

    @Transactional
    public static List<Document> loadDocuments(List<Long> idList) {
        return dataService.loadDocuments(idList);
    }

    @Transactional
    public static boolean saveDocument(Document document) {
        return dataService.saveDocument(document);
    }

    @Transactional
    public static boolean removeDocument(Long id) {
        return dataService.removeDocument(id);
    }

    @Transactional
    public static List<List<Integer>> loadPostings(List<Term> terms) {
        return dataService.loadPostings(terms);
    }

    @Transactional
    public static boolean savePostings(List<Integer> postings, Long termId) {
        return dataService.savePostings(postings, termId);
    }

    @Transactional
    public static boolean removePostings(Long termId) {
        return dataService.removePostings(termId);
    }

    @Transactional
    public static Indexator loadIndex() {
        return dataService.loadIndex();
    }

    @Transactional
    public static boolean saveIndex() {
        return dataService.saveIndex(Indexator.getIndexator());
    }

    @Transactional
    public static boolean removeIndex() {
        return dataService.removeIndex();
    }

    static class DataServiceImpl {

        JdbcTemplate jdbcTemplate;
        SimpleJdbcInsert jdbcTermsInsert;
        SimpleJdbcInsert jdbcDocsInsert;

        private void setDataSource(DataSource dataSource) {
            jdbcTemplate      = new JdbcTemplate(dataSource);
            jdbcTermsInsert   = new SimpleJdbcInsert(dataSource)
                    .withTableName("search_system.terms")
                    .usingGeneratedKeyColumns("id")
                    .usingColumns("value");
            jdbcDocsInsert    = new SimpleJdbcInsert(dataSource)
                    .withTableName("search_system.documents")
                    .usingColumns("id", "content");
//            jdbcIndexInsert   = new SimpleJdbcInsert(dataSource)
//                    .withTableName("search_system.terms")
//                    .usingGeneratedKeyColumns("id")
//                    .usingColumns("value");
        }

        private Term loadTerm(Long id) {
            Term term;
            try {
                term = this.jdbcTemplate.queryForObject(
                        "SELECT id, value FROM search_system.terms WHERE id=?",
                        ParameterizedBeanPropertyRowMapper.newInstance(Term.class),
                        id);
            }
            catch (EmptyResultDataAccessException ex) {
                throw new ObjectRetrievalFailureException(Term.class, new Long(id));
            }
            return term;
        }

        private boolean saveTerm(Term term) {
            int result = 0;
            if (term.isNew()) {
                Number newKey = this.jdbcTermsInsert.executeAndReturnKey(
                        new BeanPropertySqlParameterSource(term));
                term.setId(newKey.longValue());
                result = 1;
//            this.jdbcTemplate.update(
//                    "INSERT INTO search_system.terms (termvalue) values(?)",
//                    term.getValue());
//            term.setId(
//                    this.jdbcTemplate.queryForLong(
//                            "SELECT termid FROM search_system.terms WHERE termvalue=?",
//                            term.getValue()));
            }
            else {
                result = this.jdbcTemplate.update(
                        "UPDATE search_system.terms SET termvalue=:value WHERE termid=:id",
                        new BeanPropertySqlParameterSource(term));
            }
            return result > 0;
        }

        private Long getTermId(String value) {
            try {
                return jdbcTemplate.queryForLong(
                        "SELECT id FROM search_system.terms WHERE value = ?",
                        value);
            }
            catch (EmptyResultDataAccessException e) {
                return new Long(-1);
            }
        }

        private boolean saveTerms(List<Term> terms) {
            int[] result;
            Long maxId = jdbcTemplate.queryForLong("SELECT max(id) from search_system.terms");
            for (Term term : terms) {
                maxId++;
                term.setId(maxId);
            }
            SqlParameterSource[] batch =
                    SqlParameterSourceUtils.createBatch(terms.toArray());
            result = this.jdbcTermsInsert.executeBatch(batch);
            for (int res : result) {
                if (res == 0) {
                    return false;
                }
            }
            return true;
        }

        private boolean removeTerm(Long id) {
            return jdbcTemplate.update(
                    "DELETE FROM search_system.terms WHERE id = ?",
                    id) > 0;
        }

        private Document loadDocument(Long id) {
            Document document;
            try {
                document = this.jdbcTemplate.queryForObject(
                        "SELECT id, content FROM search_system.documents WHERE id=?",
                        ParameterizedBeanPropertyRowMapper.newInstance(Document.class),
                        id);
            }
            catch (EmptyResultDataAccessException ex) {
                throw new ObjectRetrievalFailureException(Document.class, new Long(id));
            }
            return document;
        }

        private List<Document> loadDocuments(List<Long> idList) {
            List<Document> documents = new ArrayList<Document>();
            if (idList.size() == 0) {
                return documents;
            }
            for (Long id : idList) {
                documents.add(loadDocument(id));
            }
            return documents;
        }

        private boolean saveDocument(Document document) {
            int result = 0;
//            if (document.isNew()) {
                document.setId(Indexator.getMaxDocId());
                result = this.jdbcDocsInsert.execute(
                        new BeanPropertySqlParameterSource(document));
//            }
//            else {
//                result = this.jdbcTemplate.update(
//                        "UPDATE search_system.terms SET termvalue=:value WHERE termid=:id",
//                        new BeanPropertySqlParameterSource(document));
//            }
            return result > 0;
        }

        private boolean removeDocument(Long id) {
            return jdbcTemplate.update(
                    "DELETE FROM search_system.documents WHERE id = ?",
                    id) > 0;
        }

        @Transactional
        private List<List<Integer>> loadPostings(List<Term> terms) {
            if (terms.size() == 0) {
                return new ArrayList<List<Integer>>();
            }
            List<List<Integer>> allPostings = new ArrayList<List<Integer>>(terms.size());
            for (Term term : terms) {
                List<Integer> postings = jdbcTemplate.queryForList(
                                "SELECT docId FROM search_system.postings WHERE termId = ?",
                                Integer.class,
                                term.getId());
                if (postings.isEmpty()) {
                    return new ArrayList<List<Integer>>(terms.size());
                }
                allPostings.add(postings);
            }
            return allPostings;
        }

        @Transactional
        private boolean savePostings(List<Integer> postings, Long termId) {
            int[] result;
            List<Object[]> batch =
                    new ArrayList<Object[]>(postings.size());
            for (Integer posting : postings) {
                batch.add(new Number[]{ termId, posting });
            }
            result = jdbcTemplate.batchUpdate(
                    "INSERT INTO search_system.postings VALUES(?, ?)",
                    batch,
                    new int[] {Type.LONG, Type.INT });
            for (int res : result) {
                if (res == 0) {
                    return false;
                }
            }
            return true;
        }

        private boolean removePostings(Long termId) {
            return jdbcTemplate.update(
                    "DELETE FROM search_system.postings WHERE termId = ?",
                    termId) > 0;
        }

        private Indexator loadIndex() {
            try {
                return jdbcTemplate.queryForObject(
                        "SELECT object FROM search_system.index " +
                                "WHERE stamp IN " +
                                "(SELECT MAX(stamp) FROM search_system.index );",
                        new RowMapper<Indexator>() {
                            public Indexator mapRow(ResultSet rs, int rowNum) throws SQLException {
                                try {
                                    byte[] buf = rs.getBytes("object");
                                    if (buf != null) {
                                        ObjectInputStream objectIn = new ObjectInputStream(
                                                new ByteArrayInputStream(buf));
                                        return (Indexator) objectIn.readObject();
                                    }
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                                } catch (IOException e) {
                                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                                }
                                return null;
                            }
                        }
                );
            }
            catch (EmptyResultDataAccessException e) {
                return  null;
            }
        }

        @Transactional
        private boolean saveIndex(final Indexator indexator) {
            return this.jdbcTemplate.update(
                    "INSERT INTO search_system.index (object) values(?)",
                    new PreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps) throws SQLException {
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            try {
                                ObjectOutputStream oout = new ObjectOutputStream(baos);
                                oout.writeObject(indexator);
                                oout.close();
                            } catch (IOException e) {
                                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                            }
                            ps.setBytes(1, baos.toByteArray());
                        }
                    }) > 0;
        }

        private boolean removeIndex() {
            return jdbcTemplate.update(
                    "DELETE FROM search_system.index") > 0;
        }
    }
}
