import React, { useEffect } from 'react';
import type { FormProps } from 'antd';
import { Button, Checkbox, Form, Input, DatePicker, Select, Card, Space, Modal } from 'antd';
import dayjs from 'dayjs';
import {PORT} from '../constants';
import axios from "axios";
import { useNavigate } from 'react-router-dom';

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

  type FieldType = {
    toDoName: string;
    toDoDate: string;
    priority: any;
    noToDoDate: any;
  };

  
  const onFinish: FormProps<FieldType>['onFinish'] = (values) => {
    console.log(currentId);

    const noDueDate = values.noToDoDate;
    let dueDateFinal = "";
    if(!noDueDate){
        dueDateFinal = dayjs(values.toDoDate).format(dateFormat); 
    }
    const toDoPriority = values.priority.value;
    const nowDate = new Date();
    let localDate = new Date(nowDate.getTime()-(nowDate.getTimezoneOffset() * 60000));

    if(currentId != -1){
        console.log("En el update");
        const newToDo = {id: currentId ,text: values.toDoName, dueDate: dueDateFinal, status: true, doneDate: null, priority: toDoPriority};
        axios.put(`http://localhost:${PORT}/todos/${currentId}`, newToDo).then((response) => { 
            fetchStats();
            setIsModalOpen(false);
            navigator('/');
        });
    }else{
        console.log("En el add");
        const newToDo = {id: -1 ,text: values.toDoName, dueDate: dueDateFinal, status: true, doneDate: null, priority: toDoPriority, creationDate : localDate.toJSON()};
        console.log(newToDo);
        //TO DO: Put a message that it was added correctly (Or there was an error)
        axios.post(`http://localhost:${PORT}/todos`, newToDo).then((response) => { 
            fetchStats();
            setIsModalOpen(false);
            navigator('/');
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
        <Modal title="To Dos App" open={isModalOpen} onCancel={handleCancel} className="modal">
        <Space direction="vertical" size={16}>
        <Card title="Change this" className='show-card'>
        <Form
          form={form}
          name="basic"
          labelCol={{ span: 10 }}
          wrapperCol={{ span: 30 }}
          style={{ maxWidth: 800 }}
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
            <Checkbox>Do not use a to do date</Checkbox>
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
            <Button type="primary" htmlType="submit" className='return-button'>
              Submit
            </Button>
          </Form.Item>

        </Form>
        </Card>
        </Space>
        </Modal>   
    </div>
  );
}

export default ModalComponent;