//
//  BroadcastVC.m
//  Hackathon
//
//  Created by XingYao on 15/10/24.
//  Copyright © 2015年 XingYao. All rights reserved.
//

#import "BroadcastVC.h"
#import "UserVO.h"
#import "XYQuick.h"
#import "MBProgressHUD.h"

#import <MessageUI/MessageUI.h>

typedef enum : NSUInteger {
    BroadcastVCAnimateRunning,
    BroadcastVCAnimateNone,
} BroadcastVCAnimate;

@interface BroadcastVC ()<MFMessageComposeViewControllerDelegate>

@property (nonatomic, strong) UIImageView *map;
@property (nonatomic, strong) NSMutableArray *users;

@property (nonatomic, strong) UIView *userInfo;
@property (nonatomic, assign) BroadcastVCAnimate animateState;
@property (nonatomic, assign) BOOL isShowedUserInfo;

@property (nonatomic, strong) UIImageView *searchView;
@end

@implementation BroadcastVC

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    self.view.backgroundColor = [UIColor whiteColor];
    
    UIImageView *imageView = [[UIImageView alloc] initWithFrame:self.view.bounds];
    imageView.image = [UIImage imageNamed:@"map_bg.png"];
    [self.view addSubview:imageView];
    self.map = imageView;
    
    UIButton *btn = [[UIButton alloc] initWithFrame:self.view.frame];
    [self.view addSubview:btn];
    [btn addTarget:self action:@selector(clickMap) forControlEvents:UIControlEventTouchUpInside];
    
    imageView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, 60, 60)];
    imageView.image = [UIImage imageNamed:@"me.png"];
    [self.view addSubview:imageView];
    [[imageView.uxy_frameBuilder centerHorizontallyInSuperview] centerVerticallyInSuperview];
    
    self.animateState = BroadcastVCAnimateNone;
    
    [self showSearch];
    
    NSTimeInterval t = 3;
    [self performSelector:@selector(hiddenSearch) withObject:nil afterDelay:t];
    
    [self performSelector:@selector(showUsers) withObject:nil afterDelay:t + 1];
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

- (void)showUsers
{
    NSMutableArray *users = [@[] mutableCopy];
    UserVO *vo;
    
    vo = [[UserVO alloc] init];
    vo.x = 100;
    vo.y = 100;
    vo.headPortrait = @"user1.png";
    [users addObject:vo];
    
    vo = [[UserVO alloc] init];
    vo.x = 200;
    vo.y = 200;
    vo.headPortrait = @"user2.png";
    [users addObject:vo];
    
    vo = [[UserVO alloc] init];
    vo.x = 150;
    vo.y = 250;
    vo.headPortrait = @"user3.png";
    [users addObject:vo];
    
    self.users = users;
    
    for (int i = 0; i < users.count; i++)
    {
        UserVO *vo = _users[i];
        
        UIButton *btn = [[UIButton alloc] initWithFrame:CGRectMake(vo.x, vo.y, 60, 60)];
        [btn setImage:[UIImage imageNamed:vo.headPortrait] forState:UIControlStateNormal];
        btn.alpha = 0;
        btn.bounds = CGRectMake(0, 0, 30, 30);
        [btn addTarget:self action:@selector(clickUser) forControlEvents:UIControlEventTouchUpInside];
        [self.view addSubview:btn];
        [self performSelector:@selector(showUserIcon:) withObject:btn afterDelay:i * 0.8];
    }
}

- (void)showUserIcon:(UIView *)view
{
    [UIView animateWithDuration:.35 delay:0 options:UIViewAnimationOptionCurveEaseInOut animations:^{
        view.alpha = 1;
        view.bounds = CGRectMake(0, 0, 60, 60);
    } completion:nil];
}

- (void)showSearch
{
    UIImageView *imageView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, 375, 103)];
    imageView.image = [UIImage imageNamed:@"search_user.png"];
    [self.view addSubview:imageView];
    self.searchView = imageView;
    [[self.searchView.uxy_frameBuilder centerHorizontallyInSuperview] alignToBottomInSuperviewWithInset:-self.searchView.bounds.size.height];
    
    [UIView animateWithDuration:.3 delay:0 options:UIViewAnimationOptionCurveEaseInOut animations:^{
        self.searchView.alpha = 1;
        [self.searchView.uxy_frameBuilder alignToBottomInSuperviewWithInset:100];
    } completion:^(BOOL finished) {
        self.animateState = BroadcastVCAnimateNone;
    }];
    
}

- (void)hiddenSearch
{
    [UIView animateWithDuration:.3 delay:0 options:UIViewAnimationOptionCurveEaseInOut animations:^{
        self.userInfo.alpha = 0;
        [[self.searchView.uxy_frameBuilder centerHorizontallyInSuperview] alignToBottomInSuperviewWithInset:-self.searchView.bounds.size.height];
    } completion:^(BOOL finished) {
        self.searchView.hidden = YES;
        [self.searchView removeFromSuperview];
        self.searchView = nil;
    }];
}

- (void)clickUser
{
    [self showUserInfo];
}

- (void)clickMap
{
    [self hiddenUserInfo];
}

- (void)clickCall
{
    NSString *str= [[NSString alloc] initWithFormat:@"telprompt://%@",@"15914140983"];
    [[UIApplication sharedApplication] openURL:[NSURL URLWithString:str]];
}

- (void)clickSMS
{
    if ([MFMessageComposeViewController canSendText])
    {
        MFMessageComposeViewController *picker = [[MFMessageComposeViewController alloc] init];
        picker.messageComposeDelegate = self;
        picker.recipients = [NSArray arrayWithObject:@"15914140983"];
        picker.body = [NSString stringWithFormat:@"您好，我是乐享用户小白，我希望能体验试用您分享在乐享上的宝贝“Ehang GHOST4轴无人机”。如若方便，麻烦回复我短信，好吗？谢谢~"];
        
        [self presentViewController:picker animated:YES completion:nil];
    }
}
#pragma mark -
- (UIView *)userInfo
{
    if (_userInfo == nil)
    {
        UIView *v = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 375, 474)];
        v.backgroundColor = [UIColor clearColor];
        _userInfo = v;
        
        UIImageView *imageView = [[UIImageView alloc] initWithFrame:v.bounds];
        imageView.image = [UIImage imageNamed:@"user1_details"];
        [v addSubview:imageView];
        
        
        UIButton *btn = [[UIButton alloc] init];
        btn.frame = CGRectMake(0, 0, 100, 44);
        [btn setTitle:@"call" forState:UIControlStateNormal];
        [btn setTitleColor:[UIColor redColor] forState:UIControlStateNormal];
        [btn addTarget:self action:@selector(clickCall) forControlEvents:UIControlEventTouchUpInside];
        [_userInfo addSubview:btn];
        [[btn.uxy_frameBuilder alignToBottomInSuperviewWithInset:20] alignLeftInSuperviewWithInset:20];
        
        btn = [[UIButton alloc] init];
        btn.frame = CGRectMake(0, 0, 100, 44);
        [btn setTitle:@"SMS" forState:UIControlStateNormal];
        [btn setTitleColor:[UIColor redColor] forState:UIControlStateNormal];
        [btn addTarget:self action:@selector(clickSMS) forControlEvents:UIControlEventTouchUpInside];
        [_userInfo addSubview:btn];
        [[btn.uxy_frameBuilder alignToBottomInSuperviewWithInset:20] alignLeftInSuperviewWithInset:120];
    }
    
    return _userInfo;
}

#pragma mark - 

- (void)showUserInfo
{
    if (self.animateState == BroadcastVCAnimateRunning || self.isShowedUserInfo == YES) {
        return;
    }
    
    self.animateState = BroadcastVCAnimateRunning;
    self.isShowedUserInfo = YES;
    self.userInfo.alpha = 0;
    [self.view addSubview:self.userInfo];
    [[self.userInfo.uxy_frameBuilder centerHorizontallyInSuperview] alignToBottomInSuperviewWithInset:-self.userInfo.bounds.size.height];
    
    [UIView animateWithDuration:.3 delay:0 options:UIViewAnimationOptionCurveEaseInOut animations:^{
        self.userInfo.alpha = 1;
        [self.userInfo.uxy_frameBuilder alignToBottomInSuperviewWithInset:0];
    } completion:^(BOOL finished) {
        self.animateState = BroadcastVCAnimateNone;
    }];
}

- (void)hiddenUserInfo
{
    if (self.animateState == BroadcastVCAnimateRunning  || self.isShowedUserInfo == NO) {
        return;
    }
    
    self.animateState = BroadcastVCAnimateRunning;
    self.isShowedUserInfo = NO;
    self.userInfo.alpha = 1;
    [self.view addSubview:self.userInfo];
    [self.userInfo.uxy_frameBuilder alignToBottomInSuperviewWithInset:0];
    
    [UIView animateWithDuration:.3 delay:0 options:UIViewAnimationOptionCurveEaseInOut animations:^{
        self.userInfo.alpha = 0;
        [[self.userInfo.uxy_frameBuilder centerHorizontallyInSuperview] alignToBottomInSuperviewWithInset:-self.userInfo.bounds.size.height];
    } completion:^(BOOL finished) {
        self.animateState = BroadcastVCAnimateNone;
    }];
}

- (void)messageComposeViewController:(MFMessageComposeViewController *)controller didFinishWithResult:(MessageComposeResult)result
{
    MBProgressHUD *hud = [MBProgressHUD showHUDAddedTo:self.view animated:YES];
    hud.mode = MBProgressHUDModeText;
    [hud hide:YES afterDelay:2];
    [controller dismissViewControllerAnimated:NO completion:^{

        switch ( result ) {
            case MessageComposeResultCancelled:
                hud.labelText = @"发送取消";
                break;
            case MessageComposeResultFailed:// send failed
                hud.labelText = @"发送失败";
                break;
            case MessageComposeResultSent:
                hud.labelText = @"发送成功";
            default:
                break;
        }
    }];
}
@end
