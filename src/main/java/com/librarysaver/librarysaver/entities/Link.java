package com.librarysaver.librarysaver.entities;


import jakarta.persistence.*;

@Entity
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column
    public String title;

    @Column
    public String description;

    @Column
    public String link;

    @ManyToOne
    @JoinColumn(name = "link_id")
    public Section section ;

    public Link(String title,  String description , String link) {
        this.title = title;
        this.description = description;
        this.link = link;
    }

    public Link() {

    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

}
