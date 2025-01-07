// フォーム画面　画面の大きさによって入力の可・不可切り替え
// DOMが読み込まれた時に実行
	document.addEventListener("DOMContentLoaded", toggleInputState);
	function toggleInputState() {
		// .tabletクラスを持つすべての要素を取得
		const mobileInputs = document.querySelectorAll('.tablet');
	 	// .large-screenクラスを持つすべての要素を取得
		const largeInputs = document.querySelectorAll('.large-screen');
		// 大画面の場合、各要素を無効化
		if (window.innerWidth >= 992) {
			mobileInputs.forEach(input => {
				input.disabled = true; // 無効化
			});
		}else {
			largeInputs.forEach(input => {
				input.disabled = true; // 無効化
			});
	 	}
	}
	
// jQuery
$(document).ready(function() {

	// 削除ボタンが押された時にアラート出現
	$('.delete').click(function() {
		if (confirm('削除してよろしいですか？') == false) return false;
	});
	
	// 会員サイドバーのスケジュールにhrefを設定
	let d = new Date();
	let ym = d.getFullYear() + "-" + d.getMonth()+1; 
	let scheduleHref = '/schedule/' + ym;
	$('.home-schedule').attr('href', scheduleHref);
	
	// 過去回覧板の選択年月取得
	$('#prev-board-ym').change(function(){
		var selectMonth = $('#prev-board-ym').val();
		let scheduleHref = '/prevboard/' + selectMonth;
		window.location.href = scheduleHref;
	})
	
	// スケジュールの選択月取得
	$('#event_ym').change(function(){
		var selectMonth = $('#event_ym').val();
		let scheduleHref = '/schedule/' + selectMonth;
		window.location.href = scheduleHref;
	});
	
	// ページ遷移後の初期化処理
    window.onload = function() {
        // 現在のURLを取得
        const url = window.location.href;
        // URLから選択された年月部分を取得（例: /schedule/2025-01）
        const match = url.match(/\/schedule\/(\d{4}-\d{2})/);
        // URLから選択された年月部分を取得（例: /prevboard/2025-01）
        const prevMatch = url.match(/\/prevboard\/(\d{4}-\d{2})/);
        if (match) {
            const selectedValue = match[1]; // yyyy-MM形式の年月を取得
            // const selectElement = document.getElementById('event_ym');
            // 値を設置
           $('#event_ym').val(selectedValue);
        } else if (prevMatch) {
            const selectedValue = prevMatch[1]; // yyyy-MM形式の年月を取得
            const selectElement = $('#prev-board-ym');
            selectElement.val(selectedValue);
         }
    };
		
	// ハンバーガーヘッダーインクルード
	//$.get('/common/hamburgerMenu.html', function(burgerHeader) {
	//	$('#header').append(burgerHeader);
	//});

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
	const prevboard = 'prevboard';
	if (location.href.indexOf(prevboard) != -1) {
		$('.sidebar > a > button').eq(1).addClass('current');
	}
	
	// スケジュールページでスケジュールボタンをカレント
	const scheduleLink = 'schedule';
	if (location.href.indexOf(scheduleLink) != -1) {
		$('.sidebar > a > button').eq(2).addClass('current');
	}


// ハンバーガーメニュー
$(document).on('click', '.nav_toggle', function() {
	console.log('クリック！');
	$('.nav_toggle, .nav').toggleClass('show');
});

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

// サインイン画面とサインアップ画面の切り替え(スライド版)
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
		// フェードアウトが終わったら2秒かけてURLを変更
    setTimeout(function(){window.location.href = '/register';},1500); // サインアップページのURL
	});
});
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
			$('#signup-con').remove();

			// サインインテンプレートをクローンして挿入
			const signinContent = $($('#signin-template').prop('content')).clone();
			$('.login-main').prepend(signinContent);
			$('#signin-con').css('display', 'block'); // 非表示解除
			//$('#signup-con').css('display', 'none'); // 非表示解除

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
			// フェードアウトが終わったら1.5秒かけてURLを変更
    setTimeout(function(){window.location.href = '/login';},1500);
		});
	});
});