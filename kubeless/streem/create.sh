#!/bin/bash

kubeless function deploy python-worker --runtime python2.7 \
                                --from-file worker.py \
                                --handler worker.run \
                                --trigger-topic start \
                                --namespace kubeless \
                                --dependencies requirements.txt
