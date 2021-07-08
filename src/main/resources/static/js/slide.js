/**
 *
 */
	swiper = new Swiper(".mySwiper", {
			slidesPerView : 3,
			spaceBetween : 30,
			slidesPerGroup : 1,
			autoplay : {
				delay : 2000,
				disableOnInteraction : false,
			},
			loop : true,
			loopFillGroupWithBlank : true,
			pagination : {
				el : ".swiper-pagination",
				clickable : true,
			},
			navigation : {
				nextEl : ".swiper-button-next",
				prevEl : ".swiper-button-prev",
			},
		});