<?php
namespace app\lib\enum;

// 由于php没有枚举类型，所以只能自己创建一个类然后定义常量
class ScopeEnum{
	const USER=16;
	const SUPER=32;
}