业务处理 Handler
包名：com.cody.handler

作用：处理App业务逻辑
Handler一共分成三个部分mapper、presenter、viewModel

模块：mapper
负责将数据模型 dataModel（Bean）转成视图模型 viewModel
针对列表做了增量刷新的处理，可以继承ModelMapper实现对应的函数即可

模块：presenter
负责处理具体业务，比如获取数据，利用mapper加工数据，通知刷新界面（酌情使用，使用observable 的 viewModel会自动刷新）

模块：viewModel
视图模型，控制视图的显示隐藏，以及界面数据，以及少量的视图逻辑