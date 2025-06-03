package com.example.demo.service;

import com.example.demo.model.Document;
import java.util.List;

public interface DocumentService {
    Document createDocument(Document document);

    List<Document> getAllDocuments();

    Document getDocumentById(Long id);

    Document updateDocument(Long id, Document document);

    Document updateDocument(Document document);

    void deleteDocument(Long id);
}
