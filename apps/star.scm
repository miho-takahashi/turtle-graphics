;;名前 : PI
;; 意味 : 円周率を定義するための関数、初期値として3.1415926
(define PI 3.1415926)

;;名前 : arg
;;意味 : 描画する図形の一つの頂点の角度を保存する変数. 初期値として0を与えている. 
(define arg 0)

;;名前 : colorNumber
;;意味 : 描画する図形の一つの頂点の角度を保存する変数. 初期値として0を与えている. 
(define colorNumber 0)

;;名前 : tx, ty
;;意味 : 亀の座標を保存する変数. 初期値として両方の変数に0を与えている. 
(define tx 200)
(define ty 200)


;;名前 : direction
;;意味 : 亀の進行方向（角度）を保存する変数. 初期値として両方の変数に0を与えている.
(define direction 0)

;;名前 : pendown
;;意味 : ペンの状態を保存する変数. 最初は何も書かない状態にしておくため, #fを初期値として与えている. 
(define pendown #f)

;;名前 : pen
;;引数 : x
;;意味 : ペンの状態（変数pendownの値）を更新する手続き. 引数xが'downであれば変数pendownの値を#tに, それ以外ならば#fにする.
(define (pen x)
  (if (eq? x 'down) (set! pendown #t) (set! pendown #f)))

;;名前 : colorjudge
;;引数 : Number
;;意味 : 変数colorNumberの値を更新する手続き. 変数colorNumberの値に引数Numberを4で割った余りの値に変更する. 
(define (colorjudge Number)
(set! (colorNumber (modulo colorNumber 4))))

;;名前 : forward
;;引数 : distance
;;意味 : 亀を移動させる手続き. 引数distance, 亀の進行方向direction, 亀の座標tx, tyから亀の移動後の座標を計算しその値を(nx, ny)に束縛する. 
;;変数pendownの値が#tの場合, 手続きdraw-lineにより視点から終点までの線を引き, 最後に移動後の座標(nx, ny)の値を(tx, ty)に束縛することで亀の現在位置を更新する. 
(define (forward distance)
  (let ((nx (+ tx (* distance (cos (/ (* PI direction) 180)))))
	(ny (+ ty (* distance (sin (/ (* PI direction) 180))))))
    (if pendown (draw-line tx ty nx ny ))
    (set! tx nx)
    (set! ty ny)))

;;名前 : left
;;引数 : angle
;;意味 : 亀をangleの分だけ左に回転させ, 回転後の角度をdirectionとする.
(define (left angle)
  (set! direction (+ direction angle)))

;;名前 : right
;;引数 : angle
;;意味 : 亀をangleの分だけ右に回転させ, 回転後の角度をdirectionとする. 
(define (right angle)
  (set! direction (- direction angle)))



;;名前 : make-args
;;引数 : node
;;意味 : これから描く図形の角を保存する関数. 引数nodeで受け取った値から描く図形の角度を求め, argに束縛する. 
(define (make-args node)
  (set! arg (- 180 (/ 180 node)))) 

;;名前 : draw-star
;;引数 : node, length, arg
;;意味 : 亀の軌道を管理する関数. 引数lengthの分だけ直線を引き, 引数argに従って回転する. この関数をnodeの値だけ再帰的に呼び出して, 図形の描画を行う. 
(define (draw-star node length arg)
  (forward length)
  (right arg)
  (if (not (= node 1))
      (draw-star (- node 1) length arg)
      )
  )

;;名前 : star
;;引数 : node, length, Number
;;意味 : 整数node, 浮動小数点数length, 整数Numberを受け取り, 星の描画を行う. 
;;Numberは描画を行う色の番号であり, ユーザから受け取ったNumberを手続きcolorjudgeに送り, その結果を元に描画する色を決定する. 
;;そして, ユーザから受け取ったnode, length, 手続きmake-argsの結果argsを手続きdraw-starに送り, 星の描画を行う. 
(define (star node length colorNumber)
;;この関数に入った際に, 描画用ウィンドウを表示する. 
(
 (colorjudge colorNumber)
 (write colorNumber)
 (newline)
  (make-args node)
  (pen 'down)
  (draw-star node length arg)
  )
)

(star 5 100 1)
