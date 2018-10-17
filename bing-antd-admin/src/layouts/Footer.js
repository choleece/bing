import React, { Fragment } from 'react';
import { Layout, Icon } from 'antd';
import GlobalFooter from '../components/GlobalFooter';

const { Footer } = Layout;
const FooterView = () => (
  <Footer style={{ padding: 0 }}>
    <GlobalFooter
      links={[
        {
          key: 'choleece 首页',
          title: 'choleece 首页',
          href: 'https://choleece.github.io',
          blankTarget: true,
        },
        {
          key: 'github',
          title: <Icon type="github" />,
          href: 'https://github.com/choleece/bing',
          blankTarget: true,
        },
        {
          key: 'Ant Design',
          title: 'Ant Design',
          href: 'https://ant.design',
          blankTarget: true,
        },
      ]}
      copyright={
        <Fragment>
          Copyright <Icon type="copyright" /> 2018 choleece 整理 ant design
        </Fragment>
      }
    />
  </Footer>
);
export default FooterView;
