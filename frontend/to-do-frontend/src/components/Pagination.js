import React from 'react';
import { Pagination } from 'antd';
const PaginationControll = () => (
  <>
    <Pagination className='pagination'
      simple={{
        readOnly: true,
      }}
      defaultCurrent={2}
      total={50}
    />
  </>
);
export default PaginationControll;