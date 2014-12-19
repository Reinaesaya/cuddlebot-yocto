build:
	docker run --rm \
	--volume /yocto:/yocto \
	--workdir /yocto \
	--tty --interactive \
	cuddlebot-dev /bin/bash -c \
	'. setup-environment build && bitbake cuddlebot-image'

sdk:
	docker run --rm \
	--volume /yocto:/yocto \
	--workdir /yocto \
	--tty --interactive \
	cuddlebot-dev /bin/bash -c \
	'. setup-environment build && bitbake cuddlebot-image -c populate_sdk'

c: console
console:
	docker run --rm \
	--volume /yocto:/yocto \
	--workdir /yocto \
	--tty --interactive \
	cuddlebot-dev /bin/bash

cvm: console-vm
console-vm:
	docker run --rm \
	--volumes-from cuddlebot-data \
	--workdir /yocto \
	--tty --interactive \
	cuddlebot-dev /bin/bash

vm:
	BOOT2DOCKER_PROFILE=$(PWD)/docker/boot2docker-profile boot2docker init

up:
	boot2docker up

down:
	boot2docker down

image:
	docker build -t cuddlebot-dev $(PWD)/docker/cuddlebot-dev

volume:
	docker run --name cuddlebot-data --volume /yocto busybox true ||:

share:
	docker run --rm \
		--volume /usr/local/bin/docker:/docker \
		--volume /var/run/docker.sock:/docker.sock \
		svendowideit/samba cuddlebot-data

.PHONY: build c cvm console console-vm down image sdk share up vm volume
