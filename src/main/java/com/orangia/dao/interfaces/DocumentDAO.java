
package com.orangia.dao.interfaces;

import com.orangia.model.Document;
import java.util.List;

public interface DocumentDAO {

    Document findById(int idDocument);

    List<Document> findAll();

    List<Document> findByType(String typeDocument);

    void save(Document document);

    void update(Document document);

    void delete(int idDocument);
}
