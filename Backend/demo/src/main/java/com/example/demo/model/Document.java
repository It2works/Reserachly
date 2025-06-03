package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = " ID_Document")
    private Long id;

    @NotBlank(message = "Le titre est obligatoire")
    private String titre;

    @NotBlank(message = "Le résumé est obligatoire")
    @Column(columnDefinition = "TEXT")
    private String resume;

    @NotNull(message = "La date de publication est obligatoire")
    @Temporal(TemporalType.DATE)
    private Date datePublication;

    @NotBlank(message = "Les mots clés sont obligatoires")
    @Column(name = "mots_cles", columnDefinition = "TEXT")
    private String motsCles;

    @NotBlank(message = "Le type de fichier est obligatoire")
    private String typeFichier;

    // New fields for file storage
    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_size")
    private Long fileSize;

    @NotNull(message = "Un auteur doit être sélectionné")
    @ManyToOne
    @JoinColumn(name = "fk_auteur")
    private Auteur auteur;

    @NotNull(message = "Un thème doit être sélectionné")
    @ManyToOne
    @JoinColumn(name = "fk_theme")
    private Theme theme;

    @NotNull(message = "Un administrateur doit être sélectionné")
    @ManyToOne
    @JoinColumn(name = "fk_admin")
    private Administrateur administrateur;

    @Column(name = "nombre_telechargements")
    private Long nombreTelechargements = 0L;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public Date getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(Date datePublication) {
        this.datePublication = datePublication;
    }

    public String getMotsCles() {
        return motsCles;
    }

    public void setMotsCles(String motsCles) {
        this.motsCles = motsCles;
    }

    public String getTypeFichier() {
        return typeFichier;
    }

    public void setTypeFichier(String typeFichier) {
        this.typeFichier = typeFichier;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Auteur getAuteur() {
        return auteur;
    }

    public void setAuteur(Auteur auteur) {
        this.auteur = auteur;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Administrateur getAdministrateur() {
        return administrateur;
    }

    public void setAdministrateur(Administrateur administrateur) {
        this.administrateur = administrateur;
    }

    public Long getNombreTelechargements() {
        return nombreTelechargements;
    }

    public void setNombreTelechargements(Long nombreTelechargements) {
        this.nombreTelechargements = nombreTelechargements;
    }

    public void incrementNombreTelechargements() {
        this.nombreTelechargements = (this.nombreTelechargements == null ? 0L : this.nombreTelechargements) + 1L;
    }
}
