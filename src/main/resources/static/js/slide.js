
swiper = new Swiper(".mySwiper", {
	slidesPerView: 3,
	spaceBetween: 30,
	slidesPerGroup: 1,
	autoplay: {
		delay: 2000,
		disableOnInteraction: 'false',
	},
	loop: 'true',
	loopFillGroupWithBlank: 'true',
	pagination: {
		el: ".swiper-pagination",
		type: 'bullets',
		clickable: 'true',
		dynamicBullets: true,
		dynamicMainBullets: 1,
	},
	navigation: {
		nextEl: ".swiper-button-next",
		prevEl: ".swiper-button-prev",
	},
});