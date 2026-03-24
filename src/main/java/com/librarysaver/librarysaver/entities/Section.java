package com.librarysaver.librarysaver.entities;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Section {



  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column
    public  String title ;

    @Column
    public  String description ;


    @Column
    public  String imagePath ;

    @ManyToOne
    @JoinColumn(name = "section_id")
    public Section parent ;

    @OneToMany( mappedBy = "parent", cascade = CascadeType.ALL , fetch = FetchType.LAZY , orphanRemoval = true )
    public List<Section> children ;

    @OneToMany(mappedBy = "section" , cascade = CascadeType.ALL , fetch = FetchType.LAZY , orphanRemoval = true)
    public List<Link> links ;


  public Section() {

  }

  public Section(String title, String description, String imagePath) {
    this.title = title;
    this.description = description;
    this.imagePath = imagePath;
  }


}
