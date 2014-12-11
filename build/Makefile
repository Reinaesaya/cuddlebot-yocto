image:
	bitbake cuddlebot-image

sdk: sanity
	bitbake cuddlebot-image -c populate_sdk

sanity: conf/sanity.conf
	touch conf/sanity.conf

.PHONY: image sdk sanity
