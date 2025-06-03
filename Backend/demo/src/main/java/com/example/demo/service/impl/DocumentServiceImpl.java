package com.example.demo.service.impl;

import com.example.demo.model.Document;
import com.example.demo.repository.DocumentRepository;
import com.example.demo.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public Document createDocument(Document document) {
        return documentRepository.save(document);
    }

    @Override
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    @Override
    public Document getDocumentById(Long id) {
        return documentRepository.findById(id).orElse(null);
    }

    @Override
    public Document updateDocument(Long id, Document document) {
        Optional<Document> existingDocument = documentRepository.findById(id);
        if (existingDocument.isPresent()) {
            document.setId(id);
            return documentRepository.save(document);
        }
        return null;
    }

    @Override
    public Document updateDocument(Document document) {
        if (document.getId() != null && documentRepository.existsById(document.getId())) {
            return documentRepository.save(document);
        }
        return null;
    }

    @Override
    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }
}
