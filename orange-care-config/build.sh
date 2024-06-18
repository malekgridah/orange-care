#!/bin/sh

# Build App
mvn clean package
# Build Docker Image
docker build --tag orange-care-config .
