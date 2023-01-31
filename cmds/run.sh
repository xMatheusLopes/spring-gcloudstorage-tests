#!/bin/sh
docker run -p 8080:8080 -v {path/to/key.json}:/tmp/gcloudkey.json --env GOOGLE_APPLICATION_CREDENTIALS=/tmp/gcloudkey.json {image-name}