package ru.nsu.fit.bd.g16203.hotelInformationSystem.entities.model;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "client")
public class Client {
    private Long clientId;
    private Set<Review> reviews = new HashSet<>(  );

    public Client() {
    }

    public Client(Long clientId) {
        this.clientId = clientId;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "CLIENT_ID", unique = true, nullable = false)
    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.ALL)
    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }
}
