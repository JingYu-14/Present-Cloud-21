<?php
namespace app\api\controller\v1;
use think\Controller;
use app\api\model\Theme as ThemeModel;
use app\lib\exception\ThemeException;

class Theme extends Controller{
	/**
	 * @url /theme/id1,id2....
	 * @return 一组theme模型
	 */
	public function getSimpleList($ids=''){
		$validate=validate('IDCollection');
        $validate->goCheck();

        $ids=explode(',',$ids);
        $result=ThemeModel::with(['topicImg','headImg'])->select($ids);
      
        if(!$result){
        	throw new ThemeException();
        }

        // 直接return一个数组会提示variable type error： array
        return json($result);
	}

	/**
	 * @url /themeproduct/id
	 * @return 某一个theme的具体模型
	 */
	public function getComplexOne($id){
		$validate=validate('IdValidate');
        $validate->goCheck();

        $theme=ThemeModel::getThemeWithProducts($id);
        if(!$theme){
            throw new ThemeException();
        }
        return json($theme);
	}
}