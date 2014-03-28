#!/bin/sh

android update project -p ./

ant clean
ant product

