import 'antd/dist/antd.css';
import './index.css';
import './App.css';
import {Button, Input, Layout, Menu, Modal, Radio, Space, Tooltip} from 'antd';
import logo from './logo.png'
import React, {useState} from 'react';
import {SettingOutlined} from '@ant-design/icons';

const {Header, Content, Footer} = Layout;

const App = () => {
    const [username, setUsername] = useState("");
    const [isRSSHintVisible, setIsRSSHintVisible] = useState("")
    const [isNewFeatureModalVisible, setIsNewFeatureModalVisible] = useState(true)
    const [ratioValue, setRatioValue] = useState("email");
    const [isValidateCodeVisible, setIsValidateCodeVisible] = useState(false);
    const [isLoginModalVisible, setIsLoginModalVisible] = useState(false);
    const [isValidateStepOneVisible, setIsValidateStepOneVisible] = useState(false);
    const [isValidationHintModalVisible, setIsValidationHintModalVisible] = useState(false);


    const showLoginModal = () => {
        setIsLoginModalVisible(true);
    };

    const handleLoginOk = async () => {

        setUsername(document.getElementById("username").value)

        const userInfo = {
            username: document.getElementById("username").value,
            password: document.getElementById("password").value,
        };

        let needValidate = false;
        let loginStatus = false;

        const response = await fetch('/login', {
            method: 'POST',
            body: JSON.stringify(userInfo),
            headers: {
                'Content-Type': 'application/json',
            },
        }).then(response => response.json()).then(json => {

            console.log(json)

            needValidate = json.needValidate === "true";
            loginStatus = json.loginStatus === "success"

            if (json.loginStatus !== "success") {
                let element = document.getElementById("showLoginStatus")
                element.textContent = "Invalid username or password, please try again!";

                setIsLoginModalVisible(loginStatus);

            } else {
                let element = document.getElementById("showLoginStatus")
                element.textContent = "";
                document.getElementById("login_button").style.visibility = "hidden";
                document.getElementById("login_button").style.width = "1px";
                document.getElementById("login_button").style.float = "right";
                document.getElementById("create_button").style.visibility = "visible";

                setIsLoginModalVisible(!loginStatus);
            }
        })

        setIsValidationHintModalVisible(needValidate);
    };

    const handleLoginCancel = () => {
        setIsLoginModalVisible(false);
    };

    const handleValidationHintOK = () => {
        setIsValidationHintModalVisible(false);
        setIsValidateStepOneVisible(true);
    };

    const handleValidationHintCancel = async () => {
        setIsValidationHintModalVisible(false);
    };

    const handleValidateStepOneOK = () => {
        setIsValidateStepOneVisible(false);
    };

    const handleValidateStepOneCancel = async () => {
        setIsValidateStepOneVisible(false);
        setIsValidationHintModalVisible(true);
    };

    const selectRadio = (e) => {
        setRatioValue(e.target.value);
    };

    const sendVerificationCode = async () => {
        let address = document.getElementById("validationAddress").value

        if (ratioValue !== "email") {
            alert("Currently only supports email!")
        }

        const response = await fetch('/sendCode', {
            method: 'POST',
            body: JSON.stringify({
                username: username,
                address: address
            }),
            headers: {
                'Content-Type': 'application/json',
            },
        }).then(response => response.json()).then(json => {

            if (json.status === "success") {
                document.getElementById("validateCodeInput").disabled = false;
                document.getElementById("validateCodeButton").disabled = false;
            }
            let element = document.getElementById("showSendCodeStatus")
            element.textContent = json.info;
        })
    }

    const checkVerificationCode = async () => {

        let code = document.getElementById("validateCodeInput").value

        const response = await fetch('/checkCode', {
            method: 'POST',
            body: JSON.stringify({
                username: username,
                code: code
            }),
            headers: {
                'Content-Type': 'application/json',
            },
        }).then(response => response.json()).then(json => {
            let element = document.getElementById("showCheckCodeStatus")
            element.textContent = json.info;

            if (json.status === "success") {
                alert("Verification passed!");
                setIsValidateStepOneVisible(false);
            }
        })
    }

    const subscribe = async () => {
        let email = document.getElementById("subscribeInput").value;

        const response = await fetch('/subscribe', {
            method: 'POST',
            body: JSON.stringify({
                email: email
            }),
            headers: {
                'Content-Type': 'application/json',
            },
        }).then(response => response.json()).then(json => {
            let element = document.getElementById("showSubscribeStatus")
            element.textContent = json.info

            if (json.status === "success") {
                document.getElementById("subscribeButton").style.visibility = 'hidden';
                document.getElementById("subscribeButton").style.float = 'left';
                document.getElementById("unsubscribeButton").style.float = '';
                document.getElementById("unsubscribeButton").style.visibility = 'visible';
            }
        })

    }
    const unsubscribe = async () => {
        let email = document.getElementById("subscribeInput").value;

        console.log(email)

        const response = await fetch('/unsubscribe', {
            method: 'POST',
            body: JSON.stringify({
                email: email
            }),
            headers: {
                'Content-Type': 'application/json',
            },
        }).then(response => response.json()).then(json => {
            let element = document.getElementById("showSubscribeStatus")
            element.textContent = json.info;
        })

    }

    const showRSSSetting = async () => {
        setIsRSSHintVisible(true);
    }

    const handleRSSHintOK = ()=>{
        setIsRSSHintVisible(false);
    }

    const handleRSSHintCancel = ()=>{
        setIsRSSHintVisible(false);
    }

    const handleNewFeatureOk = ()=>{
        setIsNewFeatureModalVisible(false);
    }

    const handleNewFeatureCancel = ()=>{
        setIsNewFeatureModalVisible(false);
    }

    const createArticle = ()=>{

    }

    return (
        <>
            <Layout className="layout">
                <Header>
                    <img className="logo" src={logo}/>
                    <Button type="primary" id="login_button"
                            onClick={showLoginModal}
                            style={{float: 'right', margin: '15px 150px 15px 0'}}>
                        Log in</Button>
                    <Button type="default" id="create_button"
                            onClick={createArticle}
                            style={{float: 'right', margin: '15px 150px 15px 0', visibility: 'hidden'}}>
                        Create</Button>
                    <Modal title="New Features" visible={isNewFeatureModalVisible} onOk={handleNewFeatureOk}
                           onCancel={handleNewFeatureCancel}>
                        <p>1. We've added a verification process via the phone number or email, which would increase the security of user accounts</p>
                        <p>2. We’ve added an RSS feed option.</p>
                    </Modal>
                    <Modal
                        title="LOG IN"
                        visible={isLoginModalVisible}
                        onOk={handleLoginOk}
                        onCancel={handleLoginCancel}
                        footer={[
                            <Button key="back" onClick={handleLoginCancel}>
                                Return
                            </Button>,
                            <Button key="submit" type="primary" onClick={handleLoginOk}>
                                Continue
                            </Button>
                        ]}
                    >
                        <Input id="username" placeholder="username"
                               style={{width: 'calc(100% - 200px)', marginTop: '10px'}}/>
                        <Input id="password" type={"password"} placeholder={"password"}
                               style={{width: 'calc(100% - 200px)', marginTop: '10px'}}/>
                        <p style={{margin: "5px", color: "red"}} id="showLoginStatus"/>
                    </Modal>
                    <Modal title="Validation Hint"
                           visible={isValidationHintModalVisible}
                           onOk={handleValidationHintOK}
                           onCancel={handleValidationHintCancel}
                           footer={[
                               <Button key="back" onClick={handleValidationHintCancel}>
                                   Cancel
                               </Button>,
                               <Button key="submit" type="primary" onClick={handleValidationHintOK}>
                                   Continue
                               </Button>
                           ]}
                    >
                        <p>
                            You are using this app for the first time or you haven't logged in for
                            30 days.
                            <br/>
                            An additional validation is recommended to enhance account security.
                            <br/>
                            You can also skip the validation by click the <b>Cancel</b> button.
                        </p>
                    </Modal>
                    <Modal title="Validation" visible={isValidateStepOneVisible}
                           onOk={handleValidateStepOneOK}
                           onCancel={handleValidateStepOneCancel}
                           footer={[
                               <Button key="back" onClick={handleValidateStepOneCancel}>
                                   Back
                               </Button>,
                               <Button key="submit" type="primary" onClick={handleValidateStepOneOK}>
                                   OK
                               </Button>
                           ]}>
                        <Radio.Group onChange={selectRadio} value={ratioValue} id={"validationRadio"}>
                            <Space direction="vertical">
                                <Radio value={"email"}>By Email</Radio>
                                <Radio value={"phone"}>By Phone Number</Radio>
                            </Space>
                        </Radio.Group>
                        <Input.Group compact style={{marginTop: '10px'}}>
                            <Input placeholder="Enter email address here" style={{width: 'calc(100% - 200px)'}}
                                   id={"validationAddress"}/>
                            <Button type="default" onClick={sendVerificationCode}>Submit</Button>
                            <p style={{margin: "5px", color: "red"}} id={"showSendCodeStatus"}/>
                        </Input.Group>
                        <Input.Group compact style={{marginTop: '10px'}} visible={isValidateCodeVisible}>
                            <Input placeholder="Enter verification code here"
                                   style={{width: 'calc(100% - 200px)'}}
                                   id={"validateCodeInput"}/>
                            <Button type="primary"
                                    id={"validateCodeButton"}
                                    onClick={checkVerificationCode}>Confirm</Button>
                            <p style={{margin: "5px", color: "red"}} id={"showCheckCodeStatus"}/>
                        </Input.Group>
                    </Modal>
                </Header>

                <Content
                    style={{
                        padding: '0 200px',
                        margin: '16px 0',
                    }}
                >
                    <Menu
                        theme="light"
                        mode="horizontal"
                        defaultSelectedKeys={["Newest"]}
                        items={[
                            {label: "Newest"},
                            {label: "Hottest"}
                        ]}
                    />
                    <div className="site-layout-content" style={{textAlign: 'center'}}>
                        Empty Content Now
                    </div>
                </Content>
                <Footer
                    style={{
                        textAlign: 'center',
                    }}
                >
                    <Input.Group compact style={{margin: '10px'}}>
                        <Input placeholder="E-mail Address"
                               style={{width: '300px'}}
                               id={"subscribeInput"}/>
                        <Button type="primary"
                                id={"subscribeButton"}
                                onClick={subscribe}
                                style={{visibility:'visible'}}>Subscribe</Button>
                        <Button type="default"
                                id={"unsubscribeButton"}
                                onClick={unsubscribe}
                                style={{visibility:'hidden',float: 'left'}}>Unsubscribe</Button>
                        <Tooltip title="Advanced Options">
                            <SettingOutlined style={{margin: '10px'}} onClick={showRSSSetting}/>
                        </Tooltip>

                    </Input.Group>
                    <p style={{margin: "5px", color: "red"}} id="showSubscribeStatus"/>
                    <Modal title="Advance Option" visible={isRSSHintVisible} onOk={handleRSSHintOK}
                           onCancel={handleRSSHintCancel}>
                        <p>
                            This is an advance option, and it needs extra knowledge of RSS.
                        </p>
                        <Button type={"link"} href={"https://en.wikipedia.org/wiki/Rashtriya_Swayamsevak_Sangh"}> RSS </Button>
                    </Modal>

                    <p>Super Blog ©2022 Created by Hui Zhang</p>
                </Footer>
            </Layout>

        </>
    );
};

export default App;
