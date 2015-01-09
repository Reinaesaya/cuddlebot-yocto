# Cuddlebot System Image

The Cuddlebot System Image is based on the
[Yocto Embedded Linux Project][yocto] and configures the Linux operating
system to allow wireless access to the Cuddlebot Control Server over Wi-Fi.


## Getting Started

The build system relies on [Docker][docker], which in turn depends on the
Linux operating system. If you are not developing on Linux, you may use
[boot2docker][boot2docker], [VirtualBox][virtualbox],
[VMWare Fusion][vmware-fusion], Parallels Desktop [parallels-desktop], or
another physical computer running Linux. Once you have access to a Linux
box, install [Docker][docker] as appropriate for your system.

After installing Docker, check out the project to `/yocto`. The
configuration files assume that the project files are available at `/yocto`
with at least 80 GB of storage space available. The build will fail if these
requirements are not met.

The Git repository depends on a number of sub-repositories. Either of these
two commands should check out these sub-repositories:

```sh
$ git checkout --recursive https://github.com/mikepb/cuddlebot-yocto.git /yocto
$ git submodule init && git submodule update
```

The first command checks out the repository to `/yocto` as required. The
second command initializes the submodules and downloads the source code.

Now, use the configuration files in the repository to configure Docker with
the build image:

```sh
$ make image
docker build -t cuddlebot-dev docker/cuddlebot-dev
Sending build context to Docker daemon  2.56 kB
Sending build context to Docker daemon 
Step 0 : FROM ubuntu:14.04
 ---> 5506de2b643b
Step 1 : MAINTAINER Michael Phan-Ba mikepb@cs.ubc.ca
 ---> Using cache
 ---> 2de08de853e4
Step 2 : RUN sed -i.bak 's/main$/main universe/' /etc/apt/sources.list
 ---> Using cache
 ---> a256984c0f45
Step 3 : RUN apt-get update
 ---> Using cache
 ---> 45e3f53d950b
Step 4 : RUN apt-get upgrade -yq
 ---> Using cache
 ---> b0d28151b673
Step 5 : RUN apt-get install -yq gawk wget git-core diffstat unzip texinfo gcc-multilib build-essential chrpath libsdl1.2-dev xterm
 ---> Using cache
 ---> 1b5a214623da
Step 6 : RUN apt-get install -yq gcc-arm-none-eabi
 ---> Using cache
 ---> 47155852b32d
Successfully built 47155852b32d
```

If you run into permission errors, you may need to run the command as root.
This command will create and register the `cuddlebot-dev` Docker image for
use with the build system. The following targets are available from the
`Makefile`:

```sh
# build the disk image
make build
# build the Linux SDK
make sdk
# start an interactive console capable of building Yocto
make c
make concole
# configure the boot2docker virtual machine using the example
# docker/boot2docker-profile configuration file
make vm
```

The disk image is saved to:

```sh
/yocto/build/tmp/deploy/images/wandboard-quad/cuddlebot-image-wandboard-quad.sdcard
```

More targets are available in the `Makefile`, but are not documented here.
As before, if you run into permission errors, you may need to run the
commands as root.

For more information about Yocto, please refer to the
[project reference manual][yocto_manual].


### Using boot2docker

If you are using [boot2docker][boot2docker], the default image size is too
small to build the disk image and the default amount of physical memory
allocated will cause performance issues. To configure boot2docker with the
appropriate settings, please see the `docker/boot2docker-profile` for
an example virtual image profile. The disk size must be at least 80 GB and
should be larger if you are including additional libraries. You should also
allocate as much memory as possible to allow the Yocto Python implementation
to handle very large dictionaries and avoid paging out to disk. The example
profile is configured for 8 GB, which may not be appropriate for your
system.


### Using Linux in a virtual machine or boot2docker

If you choose to run Docker in a virtual machine or using boot2docker (which
uses [VirtualBox][virtualbox]), you may be able to export `/yocto` using
[NFS][nfs]. Install NFS as appropriate for your server and configure it to
export `/yocto` with full access.

On Ubuntu 14.04 LTS, you may use [this guide][ubuntu-nfs] to get started.
You may then configure NFS through the `/etc/exports` configuration file:

```
# /etc/exports: the access control list for filesystems which may be exported
#               to NFS clients.  See exports(5).
#
# Example for NFSv2 and NFSv3:
# /srv/homes       hostname1(rw,sync,no_subtree_check) hostname2(ro,sync,no_subtree_check)
#
# Example for NFSv4:
# /srv/nfs4        gss/krb5i(rw,sync,fsid=0,crossmnt,no_subtree_check)
# /srv/nfs4/homes  gss/krb5i(rw,sync,no_subtree_check)
#

/yocto       172.16.1.0/24(rw,sync,nohide,no_subtree_check,insecure,fsid=0,all_squash,anonuid=0)
```

This particular configuration exports the `/yocto` folder to the
`172.16.1.0/24` subnet without encryption with all access mapped to root.
This specific subnet describes the private network with the host machine.

On OS X, you may then connect to the export through the Finder using the
virtual machine IP address, for example `nfs://172.16.1.128/yocto`. Adjust
the IP address as appropriate for your setup.


## Project File Organization

- `build/` the build directory (DO NOT REMOVE)
- `docker/` [Docker][docker] configuration files
- `downloads/` [Yocto][yocto] download cache
- `sources/` [Yocto][yocto] source files
- `sources/meta-cuddlebot` Cuddlebot-specific configuration files
- `sstate-cache/` [Yocto][yocto] build cache
- `LICENSE.txt` project licence
- `README.md` a getting started guide
- `Makefile` a `Makefile`
- `setup-environment` the environment setup script


## License

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.


[boot2docker]: http://boot2docker.io
[docker]: https://www.docker.com
[nfs]: https://en.wikipedia.org/wiki/Network_File_System
[parallels-desktop]: http://www.parallels.com/ca/products/desktop/
[ubuntu-nfs]: https://help.ubuntu.com/community/SettingUpNFSHowTo
[virtualbox]: https://www.virtualbox.org
[vmware-fusion]: https://www.vmware.com/products/fusion/features.html
[vmware]: https://www.vmware.com
[yocto]: https://www.yoctoproject.org
[yocto_manual]: http://www.yoctoproject.org/docs/current/ref-manual/ref-manual.html
