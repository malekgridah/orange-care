#!/bin/bash
docker push ${REPOSITORY_PREFIX}/orange-care-registry:${VERSION}
docker push ${REPOSITORY_PREFIX}/orange-care-config:${VERSION}
docker push ${REPOSITORY_PREFIX}/orange-care-gateway:${VERSION}
docker push ${REPOSITORY_PREFIX}/orange-care-customers:${VERSION}
docker push ${REPOSITORY_PREFIX}/orange-care-bscs:${VERSION}
docker push ${REPOSITORY_PREFIX}/orange-care-contracts${VERSION}
docker push ${REPOSITORY_PREFIX}/orange-care-admin:${VERSION}
