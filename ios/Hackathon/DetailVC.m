//
//  DetailVC.m
//  Hackathon
//
//  Created by XingYao on 15/10/24.
//  Copyright © 2015年 XingYao. All rights reserved.
//

#import "DetailVC.h"
#import "XYQuick.h"
#import "BroadcastVC.h"
#import "UIImageView+WebCache.h"
#import "MBProgressHUD.h"

@interface DetailVC ()
@property (nonatomic, strong) UIImageView *photoView;
@property (nonatomic, strong) UILabel *lableName;
@property (nonatomic, strong) UILabel *lableBrand;

@property (nonatomic, strong) UIButton *btnShare;
@property (nonatomic, strong) UIButton *btnTry;
@end

@implementation DetailVC

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.title = @"产品信息";
    self.view.backgroundColor = [UIColor whiteColor];
    
    UIImageView *imageView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, 300, 300)];
    [self.view addSubview:imageView];
    [[imageView.uxy_frameBuilder centerHorizontallyInSuperview] alignToTopInSuperviewWithInset:100];
    self.photoView = imageView;
    self.photoView.image = self.photo;
    
    UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, 300, 44)];
    label.textColor = [UIColor blackColor];
    label.text = @"产品名称: 亿航 无人机";
    label.font = [UIFont systemFontOfSize:12];
    [self.view addSubview:label];
    [[label.uxy_frameBuilder centerHorizontallyInSuperview] alignToBottomOfView:self.photoView offset:20];
    self.lableName = label;
    
    label = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, 200, 44)];
    label.textColor = [UIColor blackColor];
    label.text = @"产品名称: 亿航 无人机";
    [self.view addSubview:label];
    [[label.uxy_frameBuilder centerHorizontallyInSuperview] alignToBottomOfView:self.lableName offset:20];
    self.lableBrand = label;
    
    UIButton *btn = [[UIButton alloc] init];
    btn.frame = CGRectMake(0, 0, 100, 44);
    [btn setTitle:@"clickShare" forState:UIControlStateNormal];
    [btn setTitleColor:[UIColor redColor] forState:UIControlStateNormal];
    [btn addTarget:self action:@selector(clickShare) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btn];
    [[btn.uxy_frameBuilder alignToBottomInSuperviewWithInset:50] alignLeftInSuperviewWithInset:30];
    self.btnShare = btn;
    
    btn = [[UIButton alloc] init];
    btn.frame = CGRectMake(0, 0, 100, 44);
    [btn setTitle:@"clickTry" forState:UIControlStateNormal];
    [btn setTitleColor:[UIColor redColor] forState:UIControlStateNormal];
    [btn addTarget:self action:@selector(clickTry) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:btn];
    [[btn.uxy_frameBuilder alignToBottomInSuperviewWithInset:50] alignLeftInSuperviewWithInset:230];
    self.btnTry = btn;
    
    if (self.goodsInfo)
    {
        self.lableName.text = self.goodsInfo[@"productName"];
        NSString *URL = [self.goodsInfo[@"imageURL"] uxy_trim];
        [self.photoView sd_setImageWithURL:[NSURL URLWithString:URL] placeholderImage:[UIImage imageNamed:@"placeholder.png"]];
    }
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/


- (void)clickShare
{
    
}

- (void)clickTry
{
    MBProgressHUD *hud = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    hud.mode = MBProgressHUDModeText;
    [hud hide:YES afterDelay:2];
    
    BroadcastVC *vc = [[BroadcastVC alloc] init];
    [self.navigationController pushViewController:vc animated:YES];
}
@end
