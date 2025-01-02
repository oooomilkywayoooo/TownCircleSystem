
// jQuery
$(document).ready(function() {

	// 削除ボタンが押された時にアラート出現
	$('.delete').click(function() {
		if(confirm('削除してよろしいですか？') == false) return false;
	});
	
	// ヘッダーインクルード
	//$.get('/common/afterLoginHeader.html', function(header) {
	//	$('#header').prepend(header);
	//});

	// ハンバーガーヘッダーインクルード
	//$.get('/common/hamburgerMenu.html', function(burgerHeader) {
	//	$('#header').append(burgerHeader);
	//});

	// サイドバーインクルード
	//$.get('/common/sideMenu.html', function(sideMenu) {
	//	$('#sidemenu').prepend(sideMenu);

		// サイドメニューのカレント表示
		// const url = 'http://127.0.0.1:5500';
		const url = 'http://localhost:8080';
		$('.sidebar').find('a').each(function(index, element) {
			const href = $(this).attr('href');
			const currentHref = url + href;
			if (currentHref === location.href) {
				// $('.sidebar > a > button').eq(index).addClass('current');
				$(this).find('button').addClass('current');
			}
		});

		// 過去回覧板ページで回覧板ボタンをカレント
		const prevbord = 'http://127.0.0.1:5500/prevbord.html';
		if (prevbord === location.href) {
			$('.sidebar > a > button').eq(1).addClass('current');
		}
	});

	// ハンバーガーメニュー
	$(document).on('click', '.nav_toggle', function() {
		console.log('クリック！');
		$('.nav_toggle, .nav').toggleClass('show');
	});

	// 回覧板を見たら「未読」->「既読」に変更
	// jQueryで実装する？検討！！
	$('.bord > a').click(function() {
		$(this).next().text('既読');
		$(this).next().addClass('already');
	})

	// 過去回覧板の表示スライダー
	$('.bxslider').bxSlider({
		auto: true,//自動切り替えの有無
		pause: 6000,//停止時間※デフォルトは4000
		speed: 1000,//動くスピード※デフォルトは500
		minSlides: 3,//一度に表示させる画像の最小値
		maxSlides: 4,//一度に表示させる画像の数
		slideWidth: 160,
		slideMargin: 10,
		pager: true,
		prevText: '＜',
		nextText: '＞',
	});

	// スケジュール画面のカレンダー変更機能
	// 「◀︎」にホバーでカーソル変更
	$('#prev-button').hover(function() {
		$(this).css('cursor', 'pointer');
	});

	// 「◀︎」クリックで前月カレンダー表示
	$('#prev-button').click(function() {
		var calAlt = $('#calendar').attr('alt');
		if (calAlt.slice(-2) == '01') {
			calAlt = calAlt - 89;
		} else {
			calAlt = calAlt - 1;
		}
		$('#calendar').attr('src', 'images/cal' + calAlt + '.png');
		$('#calendar').attr('alt', calAlt);
		$('#calendar').parent().find('a').attr('href', 'images/cal' + calAlt + '.png');
	});

	// 「▶︎」にホバーでカーソル変更
	$('#next-button').hover(function() {
		$(this).css('cursor', 'pointer');
	});

	// 「▶︎」クリックで次月カレンダー表示
	$('#next-button').click(function() {
		var calAlt = $('#calendar').attr('alt');
		var intCalAlt = parseInt(calAlt);
		if (calAlt.slice(-2) == '12') {
			intCalAlt = intCalAlt + 89;
		} else {
			intCalAlt = intCalAlt + 1;
		}
		$('#calendar').attr('src', 'images/cal' + intCalAlt + '.png');
		$('#calendar').attr('alt', intCalAlt);
		$('#calendar').parent().find('a').attr('href', 'images/cal' + intCalAlt + '.png');
	});

	// サインイン画面とサインアップ画面の切り替え(フェードアウト版)
	// 外部テンプレートを読み込み
	// $.get('signinTemplate.html', function(templates) {
	//     // テンプレートを非表示でbodyに追加
	//     $('body').append(templates);

	//     // サインインボタンがクリックされた場合
	//     $(document).on('click', '#changeSignIn', function () {
	//         // 現在のサインアップの要素を非表示にする
	//         $('#signup-con').fadeOut(1000, function () {
	//             // 古い要素を削除
	//             $('#signup-con').remove();

	//             // サインインテンプレートをクローンして挿入
	//             const signinContent = $($('#signin-template').prop('content')).clone();
	//             $('main').prepend(signinContent);
	//             $('#signin-con').fadeIn(1000); // フェードイン
	//         });
	//     });

	//     // サインアップボタンがクリックされた場合
	//     $(document).on('click', '#changeSignUp', function () {
	//         // 現在のサインインの要素を非表示にする
	//         $('#signin-con').fadeOut(1000, function () {
	//             // 古い要素を削除
	//             $('#signin-con').remove();

	//             // サインアップテンプレートをクローンして挿入
	//             const signupContent = $($('#signup-template').prop('content')).clone();
	//             $('main').prepend(signupContent);
	//             $('#signup-con').fadeIn(1000); // フェードイン
	//         });
	//     });
	// });

	// サインイン画面とサインアップ画面の切り替え(スライド版)
	// 外部テンプレートを読み込み
//	$.get('/signinTemplate', function(templates) {
	//	console.log(templates);
		// テンプレートを非表示でbodyに追加
	//	$('body').append(templates);

		// サインアップボタンがクリックされた場合
		$(document).on('click', '#changeSignUp', function() {
			// アニメーション対象を指定
			const rightSlideOut = $('.signup-view');
			const leftSlideOut = $('.signin-view');

			// 左右から中央に向かうアニメーション（スライドアウト）
			rightSlideOut.addClass('slide-out').on('animationend', function() {
				rightSlideOut.removeClass('slide-out');
			});

			leftSlideOut.addClass('slide-out').on('animationend', function() {
				leftSlideOut.removeClass('slide-out');

				// 古い要素を削除
				$('#signin-con').remove();

				// サインアップテンプレートをクローンして挿入
				const signupContent = $($('#signup-template').prop('content')).clone();
				$('.login-main').prepend(signupContent);
				$('#signup-con').css('display', 'block'); // 非表示解除

				// console.log(signupContent);

				// 挿入された要素を取得
				const newContainer = $('#signup-con');
				const rightSlideIn = newContainer.find('.sign_up');
				const leftSlideIn = newContainer.find('.sign_in');

				// 中央から左右に向かうアニメーション（スライドイン）
				rightSlideIn.addClass('slide-in').on('animationend', function() {
					rightSlideIn.removeClass('slide-in');
				});

				leftSlideIn.addClass('slide-in').on('animationend', function() {
					leftSlideIn.removeClass('slide-in');
				});
			});
//		});

		// サインインボタンがクリックされた場合
		$(document).on('click', '#changeSignIn', function() {
			// アニメーション対象を指定
			const rightSlideOut = $('.sign_up');
			const leftSlideOut = $('.sign_in');

			// 左右から中央に向かうアニメーション（スライドアウト）
			rightSlideOut.addClass('slide-out').on('animationend', function() {
				rightSlideOut.removeClass('slide-out');
			});

			leftSlideOut.addClass('slide-out').on('animationend', function() {
				leftSlideOut.removeClass('slide-out');

				// 古い要素を削除
				$('#signin-con').remove();

				// サインインテンプレートをクローンして挿入
				const signinContent = $($('#signin-template').prop('content')).clone();
				$('main').prepend(signinContent);
				$('#signin-con').css('display', 'block'); // 非表示解除
				$('#signup-con').css('display', 'none'); // 非表示解除

				// console.log(signupContent);

				// 挿入された要素を取得
				const newContainer = $('#signin-con');
				const rightSlideIn = newContainer.find('.signup-view');
				const leftSlideIn = newContainer.find('.signin-view');

				// 中央から左右に向かうアニメーション（スライドイン）
				rightSlideIn.addClass('slide-in').on('animationend', function() {
					rightSlideIn.removeClass('slide-in');
				});

				leftSlideIn.addClass('slide-in').on('animationend', function() {
					leftSlideIn.removeClass('slide-in');
				});
			});
		});
	});
//});
