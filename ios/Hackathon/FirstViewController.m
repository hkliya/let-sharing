//
//  FirstViewController.m
//  Hackathon
//
//  Created by XingYao on 15/10/24.
//  Copyright © 2015年 XingYao. All rights reserved.
//

#import "FirstViewController.h"
#import "AppDelegate.h"
#import "XYQuick.h"
#import "CameraSessionView.h"
#import "DetailVC.h"
#import "ScanVC.h"
#import "AppDelegate.h"

#import "UIImageView+WebCache.h"

@interface FirstViewController ()<CACameraSessionDelegate, AVCaptureMetadataOutputObjectsDelegate>
@property (nonatomic, strong) UIButton *btnCam;
@property (nonatomic, strong) UIImageView *photoView;
@property (nonatomic, strong) CameraSessionView *cameraView;
@property (nonatomic, strong) UIButton *btnUpload;

@property (nonatomic, strong) NSDictionary *goodsInfo;

@end

@implementation FirstViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    self.view.backgroundColor = [UIColor whiteColor];
    
    UIImageView *imageView;
    imageView = [[UIImageView alloc] initWithFrame:self.view.frame];
    imageView.image = [UIImage imageNamed:@"frist_bg.png"];
    [self.view addSubview:imageView];

    
    UIButton *btn = [[UIButton alloc] init];
    btn.frame = CGRectMake(0, 0, 100, 44);
    [btn setTitle:@"cam" forState:UIControlStateNormal];
    [btn setTitleColor:[UIColor redColor] forState:UIControlStateNormal];
    [btn addTarget:self action:@selector(clickCam) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btn];
    [[btn.uxy_frameBuilder alignToBottomInSuperviewWithInset:250] alignLeftInSuperviewWithInset:30];
    self.btnCam = btn;
    
    btn = [[UIButton alloc] init];
    btn.frame = CGRectMake(0, 0, 100, 44);
    [btn setTitle:@"upload" forState:UIControlStateNormal];
    [btn setTitleColor:[UIColor redColor] forState:UIControlStateNormal];
    [btn addTarget:self action:@selector(clickUpload) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btn];
    [[btn.uxy_frameBuilder alignToBottomInSuperviewWithInset:350] alignLeftInSuperviewWithInset:230];
    self.btnCam = btn;
    
    imageView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, 300, 300)];
    [self.view addSubview:imageView];
    [[imageView.uxy_frameBuilder centerHorizontallyInSuperview] alignToTopInSuperviewWithInset:100];
    self.photoView = imageView;
    
    //self.tvc.navigationController.navigationBar.hidden = YES;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


- (void) viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
    [self.navigationController setNavigationBarHidden:YES animated:YES];
}
- (void) viewDidDisappear:(BOOL)animated
{
    [super viewDidDisappear:animated];
    [self.navigationController setNavigationBarHidden:NO animated:YES];
}

- (void)clickCam
{
    UIActionSheet *actionSheet = [[UIActionSheet alloc] initWithTitle:nil delegate:nil cancelButtonTitle:@"取消" destructiveButtonTitle:@"拍照" otherButtonTitles:@"扫一扫", @"从手机相册选择", nil];
    [actionSheet uxy_handlerClickedButton:^(UIActionSheet *actionSheet, NSInteger btnIndex) {
        NSLogD(@"%@", @(btnIndex));
        if (btnIndex == 0)
        {
            _cameraView = [[CameraSessionView alloc] initWithFrame:self.view.frame];
            _cameraView.delegate = self;
            _cameraView.hidden = NO;
            
            UIViewController *tvc = [(AppDelegate *)[UIApplication sharedApplication].delegate tabBarController];
            [tvc.view addSubview:_cameraView];
        }
        else if (btnIndex == 1)
        {
            [self gotoScanVC];
        }
    }];
    [actionSheet showInView:self.view];
}

- (void)clickUpload
{
    DetailVC *vc = [[DetailVC alloc] init];
    vc.photo = self.photoView.image;
    vc.goodsInfo = self.goodsInfo;

    [self.tvc.navigationController pushViewController:vc animated:YES];
}

#pragma mark - 
- (NSString *)tabImageName
{
    return @"1";
}

- (NSString *)tabTitle
{
    return @"拍照";
}

#pragma mark -
- (void)gotoScanVC
{
    ScanVC *vc = [[ScanVC alloc] init];
    [self.navigationController pushViewController:vc animated:YES];
    
    uxy_def_weakSelf
    [vc uxy_receiveObject:^(NSString *QRCode) {
        uxy_def_strongSelf;
        NSString *str = [QRCode uxy_URLDecoding];
        NSLogD(@"%@", str);
        self.goodsInfo = [str uxy_JSONValue];
        NSString *URL = [self.goodsInfo[@"imageURL"] uxy_trim];
        [self.photoView sd_setImageWithURL:[NSURL URLWithString:URL] placeholderImage:[UIImage imageNamed:@"placeholder.png"]];
    } withIdentifier:@"QRCode"];
}
#pragma mark - CACameraSessionDelegate
- (void)didCaptureImage:(UIImage *)image
{
    float side = image.size.width;
    self.photoView.image = [[image uxy_fixOrientation]
                            uxy_crop:CGRectMake(0,
                                                (image.size.height - side) / 2,
                                                side,
                                                side)];
    [_cameraView onTapDismissButton];
    _cameraView = nil;
}
@end
