package vip.paolu.wm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.paolu.wm.entity.AddressBook;
import vip.paolu.wm.mapper.AddressBookMapper;
import vip.paolu.wm.service.AddressBookService;

/**
 * Description:
 * User: Pan
 * Date: 2023-03-17-0:54
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
