#!/bin/sh

cd orange-care-registry
./build.sh
cd ..

cd orange-care-config
./build.sh
cd ..

cd orange-care-admin
./build.sh
cd ..

cd orange-care-bscs
./build.sh
cd ..

cd orange-care-gateway
./build.sh
cd ..

cd orange-care-ui
./build.sh
cd ..

docker-compose up -d
