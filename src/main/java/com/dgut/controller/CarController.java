package com.dgut.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "二手车业务控制器", description = "二手车业务控制器")
public class CarController extends BaseController{
//
//    @Autowired
//    CarServiceImpl carService;
//
//    @Autowired
//    CarCommentServiceImpl carCommentService;
//
//    @GetMapping("/public/car/brand")
//    @ApiOperation("[品牌]公共查询")
//    public Result<?> searchCarByBrand(String brand) {
//        List<CarEntity> cars = carService.findContainsBrand(brand);
//        return ResultUtils.success(cars.stream().map(CarInfoVO::new));
////        return ResultUtils.success(cars);
//    }
//    @GetMapping("/public/car")
//    @ApiOperation("获取公共二手车信息列表")
//    public Result<?> getCarList() {
//        List<CarEntity> cars = carService.findAllByPublishIsTrue();
//        return ResultUtils.success(cars.stream().map(CarInfoVO::new));
//    }
//    @GetMapping("/public/car/{id}")
//    @ApiOperation("获取公共二手车详细信息")
//    public Result<?> getCarDetail(@PathVariable(value = "id",required = true) Integer id) {
//        CarEntity car = carService.findById(id);
//        List<CarCommentEntity> comments = carCommentService.getCommentsByCar(car);
//        return ResultUtils.success(new CarInfoWithCommentVO(car,comments));
//    }
//    @PostMapping("/car")
//    @ApiOperation("新增二手车信息")
//    @PreAuthorize("hasRole('user')")
//    public Result<?> createCar(CarHandleDto carDto, Authentication authentication) {
//        carService.save(carDto,authentication.getName());
//        return ResultUtils.success("二手车信息创建成功");
//    }
//    @PutMapping("/car")
//    @ApiOperation("更新二手车信息")
//    @PreAuthorize("hasRole('user')")
//    public Result<?> updateCar(CarHandleDto carDto, Authentication authentication) {
//        try {
//            carService.update(carDto);
//        } catch (RuntimeException e) {
//            return ResultUtils.error(-1,e.getMessage());
//        }
//        return ResultUtils.success("二手车信息更新成功");
//    }
//    @GetMapping("/car")
//    @ApiOperation("获取当前用户二手车列表")
//    @PreAuthorize("hasRole('user')")
//    public Result<?> getSelfList(Authentication authentication) {
//        List<CarEntity> cars = carService.findAllBySelf(authentication.getName());
//        return ResultUtils.success(cars.stream().map(CarInfoVO::new));
//    }
//    @PutMapping("/car/publish")
//    @ApiOperation("修改二手车发布状态")
//    @PreAuthorize("hasRole('user') OR hasRole('admin')")
//    public Result<?> changePublish(Integer car_id, Integer is_publish) {
//        CarHandleDto carDto = new CarHandleDto();
//        carDto.setId(car_id);
//        carDto.setIsPublish(is_publish);
//        return ResultUtils.success(new CarInfoVO(carService.changeCarInfo(carDto)));
//    }



}
