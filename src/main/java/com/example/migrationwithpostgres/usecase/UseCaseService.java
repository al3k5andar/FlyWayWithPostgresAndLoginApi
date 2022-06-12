package com.example.migrationwithpostgres.usecase;

public interface UseCaseService <T, R>{

    T execute(R r);
}
