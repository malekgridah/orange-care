#!/bin/bash

docker tag ${REPOSITORY_PREFIX}/orange-care-registry ${REPOSITORY_PREFIX}/orange-care-registry:${VERSION}
docker tag ${REPOSITORY_PREFIX}/orange-care-config ${REPOSITORY_PREFIX}/orange-care-config:${VERSION}
docker tag ${REPOSITORY_PREFIX}/orange-care-gateway ${REPOSITORY_PREFIX}/orange-care-gateway:${VERSION}
docker tag ${REPOSITORY_PREFIX}/orange-care-customers ${REPOSITORY_PREFIX}/orange-care-customers:${VERSION}
docker tag ${REPOSITORY_PREFIX}/orange-care-bscs ${REPOSITORY_PREFIX}/orange-care-bscs:${VERSION}
docker tag ${REPOSITORY_PREFIX}/orange-care-contracts ${REPOSITORY_PREFIX}/orange-care-contracts:${VERSION}
docker tag ${REPOSITORY_PREFIX}/orange-care-admin ${REPOSITORY_PREFIX}/orange-care-admin:${VERSION}
