#!/bin/bash

mongoimport --db xmemedb --collection greetings --drop --jsonArray --file ./sample-data.json