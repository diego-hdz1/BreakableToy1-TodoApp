import React, { useEffect } from 'react';
import type { FormProps } from 'antd';
import { Button, Checkbox, Form, Input, DatePicker, Select, Card, notification, Modal } from 'antd';
import type { NotificationArgsProps } from 'antd';
import dayjs from 'dayjs';
import {PORT} from '../constants';
import axios from "axios";
import { useNavigate } from 'react-router-dom';

type NotificationPlacement = NotificationArgsProps['placement'];

interface ModalProps{
    isModalOpen: boolean;
    currentId: number;
    fetchStats: () => void;
    setIsModalOpen: (data: boolean) => void;
    
}

const ModalComponent: React.FC<ModalProps> = ({
    currentId,
    isModalOpen,
    fetchStats,
    setIsModalOpen
}) => {
  const today = dayjs();
  const dateFormat = 'YYYY-MM-DD';
  const [form] = Form.useForm();
  const navigator = useNavigate();

  const [api, contextHolder] = notification.useNotification();

  const openNotification = (placement: NotificationPlacement) => {
    api.success({
        message: 'To-Do Saved Successfully',
        description: 'Your to-do item has been saved.',
        placement,
      });      
  };

  const handleSuccessLogic = () => {
    fetchStats();
    form.resetFields();
    setIsModalOpen(false);
    openNotification('bottomRight')
    navigator('/');     
  };

  type FieldType = {
    toDoName: string;
    toDoDate: string;
    priority: any;
    noToDoDate: any;
  };
  
  const onFinish: FormProps<FieldType>['onFinish'] = (values) => {

    const noDueDate = values.noToDoDate;
    let dueDateFinal = "";
    if(!noDueDate){
        dueDateFinal = dayjs(values.toDoDate).format(dateFormat); 
    }
    const toDoPriority = values.priority.value;
    const nowDate = new Date();
    let localDate = new Date(nowDate.getTime()-(nowDate.getTimezoneOffset() * 60000));

    if(currentId != -1){
        const newToDo = {id: currentId ,text: values.toDoName, dueDate: dueDateFinal, status: true, doneDate: null, priority: toDoPriority};
        axios.put(`http://localhost:${PORT}/todos/${currentId}`, newToDo).then((response) => { 
            handleSuccessLogic();
        });
    }else{
        const newToDo = {id: -1 ,text: values.toDoName, dueDate: dueDateFinal, status: true, doneDate: null, priority: toDoPriority, creationDate : localDate.toJSON()};
        axios.post(`http://localhost:${PORT}/todos`, newToDo).then((response) => { 
            handleSuccessLogic();
        });
    }
  };

  const handleCancel = () => {
    setIsModalOpen(false);
  };  
  
  const onFinishFailed: FormProps<FieldType>['onFinishFailed'] = (errorInfo) => {
    console.log('Failed:', errorInfo);
  };

    useEffect(()=>{
        if(currentId != -1){
            axios.get(`http://localhost:${PORT}/todos/${currentId}`).then((response)=>{
                console.log(response.data);
                const labelFinal = response.data.priority === 1 ? 'Low' : response.data.priority === 2 ? 'Medium' : response.data.priority === 3 ? 'High' : '';
                const valueFinal = parseInt(response.data.priority);
                const initialValues = {
                    toDoName: response.data.text,
                    toDoDate: dayjs(response.data.dueDate),
                    priority: {value: valueFinal, label: labelFinal}
                  };
                form.setFieldsValue(initialValues);
            }).catch(error =>{console.log(error);})
        }
    }, [currentId])

  return(
    <div>
        {contextHolder}
        <Modal title="To Dos App" open={isModalOpen} onCancel={handleCancel} width={"50em"}>
        <Card title="Enter de information for the To" className='show-card'>
        <Form
          form={form}
          name="basic"
          initialValues={{ remember: true, nonStop: false }}
          onFinish={onFinish}
          onFinishFailed={onFinishFailed}
          autoComplete="off"
        >
          <Form.Item<FieldType>
            label="To Do"
            name={"toDoName"}
            rules={[{ required: true, message: 'Please input the To Do Name' }]}
          >

            <Input />
          </Form.Item>


          <Form.Item<FieldType>
            label="To Do Date"
            name={"toDoDate"}
          >
          <DatePicker minDate={today} />
          </Form.Item>

          <Form.Item name="noToDoDate" valuePropName="checked">
            <Checkbox onClick={()=>{form.setFieldsValue({toDoDate: null})}}>Do not use a to do date</Checkbox>
          </Form.Item>

          <Form.Item label="Priority: " name={"priority"} rules={[{ required: true, message: 'Please select a priority' }]}    >
          <Select
              labelInValue = {true}
              style={{ width: 120 }}
              options={[
              {
                  value: 1,
                  label: 'Low',
              },
              {
                  value: 2,
                  label: 'Medium',
              },
              {
                  value: 3,
                  label: 'High',
              },
              ]}
          />
          
          </Form.Item>

          <Form.Item label={null}>
            <Button type="primary" htmlType="submit" >
              Submit
            </Button>
          </Form.Item>

        </Form>
        </Card>
        </Modal>   
        
    </div>
  );
}

export default ModalComponent;