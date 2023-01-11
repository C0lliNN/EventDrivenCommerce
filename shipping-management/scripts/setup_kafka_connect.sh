#!/usr/bin/env bash

MYDIR="$(dirname "$(realpath "$0")")"

CONTENT=$(cat $MYDIR/kafka-connect-config.json)
echo "$CONTENT"

curl --location --request POST 'http://localhost:8083/connectors/' \
--header 'Content-Type: application/json' \
--data-raw "$CONTENT"