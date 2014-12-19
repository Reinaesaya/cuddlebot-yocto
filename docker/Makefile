c: console
console:
	docker run --rm \
	--volumes-from cuddlebot-data \
	--workdir /yocto \
	--tty --interactive \
	cuddlebot-dev /bin/bash

cl: console-linux
console-linux:
	docker run --rm \
	--volume /yocto:/yocto \
	--workdir /yocto \
	--tty --interactive \
	cuddlebot-dev /bin/bash

boot2docker:
	BOOT2DOCKER_PROFILE=$(PWD)/boot2docker-profile boot2docker init

up:
	boot2docker up

down:
	boot2docker down

images:
	docker build -t cuddlebot-dev cuddlebot-dev

volume:
	docker run --name cuddlebot-data --volume /yocto busybox true ||:

share:
	docker run --rm \
		--volume /usr/local/bin/docker:/docker \
		--volume /var/run/docker.sock:/docker.sock \
		svendowideit/samba cuddlebot-data

.PHONY: boot2docker c cl console console-linux down images serialproxy share up volume
