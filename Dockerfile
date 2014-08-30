# Wandboard build environment

# Use the Ubuntu base image provided by dotCloud
FROM ubuntu:14.04
MAINTAINER Michael Phan-Ba mikepb@cs.ubc.ca

# Update the APT cache
RUN sed -i.bak 's/main$/main universe/' /etc/apt/sources.list
RUN apt-get update
RUN apt-get upgrade -yq

# Install dependencies for Yocto
RUN apt-get install -yq gawk wget git-core diffstat unzip texinfo gcc-multilib build-essential chrpath libsdl1.2-dev xterm

# Install dependencies for ChibiOS
RUN apt-get install -yq gcc-arm-none-eabi
