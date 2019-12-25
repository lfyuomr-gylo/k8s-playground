#!/bin/sh

mongo \
  -u $MONGO_INITDB_ROOT_USERNAME \
  -p $MONGO_INITDB_ROOT_PASSWORD \
  --authenticationDatabase admin \
  --eval "db.createUser({
    \"user\": \"$SYMBOLS_USER\",
    \"pwd\": \"$SYMBOLS_PASSWORD\",
    \"roles\": [{
      \"role\": \"readWrite\", \"db\": \"$SYMBOLS_DBNAME\"
    }]
  })" \
  admin