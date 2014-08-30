
sanity: conf/sanity.conf
	touch conf/sanity.conf

sdk: sanity
	bitbake core-image-minimal -c populate_sdk

.PHONY: sanity sdk
