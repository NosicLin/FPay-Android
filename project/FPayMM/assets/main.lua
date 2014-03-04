scene=Scene:create()

layer=Layer2D:create()

scene:push(layer)
layer:setViewArea(0,0,960,640)
layer:setTouchEnabled(true)
layer:setDispatchTouchEnabled(true)





local b1=Button:create()
b1:setTouchEnabled(true);

b1_nstate=b1:getNormalState();
b1_nstate:setTexture("play_up.png")

print(Button.FLAG_TEXTURE+Button.FLAG_SCALE)
b1_nstate:setFlag(Button.FLAG_TEXTURE+Button.FLAG_SCALE)

b1_pstate=b1:getPressState();
b1_pstate:setTexture("play_down.png")

b1_pstate:setScale(Vector3(1.2,1.2,1))
b1_pstate:setFlag(Button.FLAG_TEXTURE+Button.FLAG_SCALE)

b1:setStateNormal();

layer:add(b1)

b1:setPosition(280,220)

b1.data={
	onClick=function(self)

		PayMM:getInstance():init("0000000000","000000000")
		f_utillog("left")
	end;
}


b1=Button:create()
b1:setTouchEnabled(true);

b1_nstate=b1:getNormalState();
b1_nstate:setTexture("play_up.png")

print(Button.FLAG_TEXTURE+Button.FLAG_SCALE)
b1_nstate:setFlag(Button.FLAG_TEXTURE+Button.FLAG_SCALE)

b1_pstate=b1:getPressState();
b1_pstate:setTexture("play_down.png")

b1_pstate:setScale(Vector3(1.2,1.2,1))
b1_pstate:setFlag(Button.FLAG_TEXTURE+Button.FLAG_SCALE)

b1:setStateNormal();

layer:add(b1)

b1:setPosition(880,450)

b1.data={
	onClick=function(self)
		PayMM:getInstance():order("fsfsf")
		f_utillog("right")
	end;
}









share:director():run(scene)


