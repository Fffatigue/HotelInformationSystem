package ru.nsu.fit.bd.g16203.hotelInformationSystem.entities.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "service")
public class Service {

    private String name;
    private Long price;
    private Set<BuildingService> buildingServices = new HashSet<>(  );
    private Set<UsedService> usedServices = new HashSet<>(  );

    public Service() {
    }

    public Service(String name, Long price) {
        this.name = name;
        this.price = price;
    }

    @Id
    @Column(name = "NAME", unique = true, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "PRICE", nullable = false)
    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "service", cascade = CascadeType.ALL)
    public Set<BuildingService> getBuildingServices() {
        return buildingServices;
    }

    public void setBuildingServices(Set<BuildingService> buildingServices) {
        this.buildingServices = buildingServices;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "service", cascade = CascadeType.ALL)
    public Set<UsedService> getUsedServices() {
        return usedServices;
    }

    public void setUsedServices(Set<UsedService> usedServices) {
        this.usedServices = usedServices;
    }
}
