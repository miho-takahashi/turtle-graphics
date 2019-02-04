; 口座オブジェクトのコンストラクタ
; 連想リストとして, キーとメソッドの組を返す.
(define (make-account)
  (let ((amount 0)) ; 残高
    (list (cons 'get-amount (lambda () amount)) ; 残高照会メソッド
	  (cons 'deposit    (lambda (x) (set! amount (+ amount x)))) ; 預け入れメソッド
	  (cons 'withdraw   (lambda (x) (set! amount (- amount x))))))) ; 引き出しメソッド

; オブジェクトにメッセージを送る.
(define (send-message obj method . args)
  (apply (cdr (assoc method obj)) args))

(define my-account (make-account))  ; 口座開設 
(write (send-message my-account 'get-amount)) ; 残高照会 => 0
(newline)

(send-message my-account 'deposit 1000) ; 預け入れ
(write (send-message my-account 'get-amount)) ; 残高照会 => 1000
(newline)

(send-message my-account 'withdraw 400) ; 引き出し
(write (send-message my-account 'get-amount)) ; 残高照会 => 600
(newline)
(exit)
