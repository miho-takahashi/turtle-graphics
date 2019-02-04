;;
;; Dragon曲線をGNUPLOT用データファイルとして出力するプログラム
;;
;; 使用方法
;; % gosh dragoncurve_gp.scm > dragoncurve.dat
;; % gnuplot
;; gnuplot> plot "dragoncurve.dat" with lines
;; 

;; 円周率
(define PI 3.1415926)

;; 亀の位置
;;
;; 座標系
;; y
;; |
;; +- x
(define tx 0)
(define ty 0)

;; 亀の進行方向
(define direction 0)

;; ペンの状態
(define pendown #f)

(define (pen x)
  (if (eq? x 'down) (set! pendown #t) (set! pendown #f)))

(define (forward distance)
  (let ((nx (+ tx (* distance (cos (/ (* PI direction) 180)))))
	(ny (+ ty (* distance (sin (/ (* PI direction) 180))))))
    (if pendown (draw-line tx ty nx ny))
    (set! tx nx)
    (set! ty ny)))

(define (left angle)
  (set! direction (+ direction angle)))

(define (right angle)
  (set! direction (- direction angle)))

;; 直線描画(gnuplotのdatafile形式)
(define (draw-line sx sy ex ey)
  (display sx) (display " ") (display sy) (newline)
  (display ex) (display " ") (display ey) (newline) 
  (newline))

;; 以下、タートルグラフィックスの描画プログラム

(define (dragon-curve-draw length level)
  (if (= level 0) 
      (forward length)
      (let ((next-length (* length (/ (sqrt 2) 2))))
	(pen 'down)
	(left 45)
	(dragon-curve-draw next-length (- level 1))

	(right 90)
	(pen 'up)
	(forward next-length)
	(right 180)
	(pen 'down)
	(dragon-curve-draw next-length (- level 1))

	(right 180)
	(pen 'up)
	(forward next-length)
	(left 45)
	(pen 'down))))

(define (dragon-curve length level)
       (pen 'down)
       (dragon-curve-draw length level))

(dragon-curve 100 10)

	

