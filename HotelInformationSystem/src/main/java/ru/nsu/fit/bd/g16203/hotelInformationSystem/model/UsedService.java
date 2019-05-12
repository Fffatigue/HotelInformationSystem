package ru.nsu.fit.bd.g16203.hotelInformationSystem.model;

public class UsedService implements Entity<UsedServiceId> {
    private UsedServiceId usedServiceId;

    public UsedService() {
    }

    public UsedService(UsedServiceId usedServiceId) {
        this.usedServiceId = usedServiceId;
    }


    @Override
    public UsedServiceId getPK() {
        return usedServiceId;
    }

    @Override
    public void setPK(UsedServiceId primaryKey) {
        usedServiceId = primaryKey;
    }
}
