image:
	bitbake core-image-minimal

sdk: sanity
	bitbake core-image-minimal -c populate_sdk

sanity: conf/sanity.conf
	touch conf/sanity.conf

.PHONY: image sdk sanity
