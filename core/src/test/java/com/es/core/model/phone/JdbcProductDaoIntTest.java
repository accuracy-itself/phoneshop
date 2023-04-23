package com.es.core.model.phone;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@ContextConfiguration("classpath:context/applicationContext-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class JdbcProductDaoIntTest {
    @Resource
    private PhoneDao phoneDao;

    private static Phone testPhone;

    @BeforeClass
    public static void init() {
        testPhone = new Phone();
        testPhone.setAnnounced(new Date());
        testPhone.setBackCameraMegapixels(new BigDecimal(10));
        testPhone.setBrand("test_brand");
        testPhone.setPrice(new BigDecimal(10));
        testPhone.setDisplaySizeInches(new BigDecimal(10));
        testPhone.setWeightGr(10);
        testPhone.setLengthMm(new BigDecimal(10));
        testPhone.setWidthMm(new BigDecimal(10));
        testPhone.setHeightMm(new BigDecimal(10));
        testPhone.setDeviceType("test_deviceType");
        testPhone.setOs("test_os");
        testPhone.setDisplayResolution("test_displayResolution");
        testPhone.setPixelDensity(10);
        testPhone.setDisplayTechnology("test_displayTechnology");
        testPhone.setFrontCameraMegapixels(new BigDecimal(10));
        testPhone.setRamGb(new BigDecimal(10));
        testPhone.setInternalStorageGb(new BigDecimal(10));
        testPhone.setBatteryCapacityMah(10);
        testPhone.setTalkTimeHours(new BigDecimal(10));
        testPhone.setStandByTimeHours(new BigDecimal(10));
        testPhone.setBluetooth("test_bluetooth");
        testPhone.setPositioning("test_positioning");
        testPhone.setImageUrl("test_imageUrl");
    }

    @Test
    public void testGetPhonesWithLimit() {
        int limit = 7;
        assertEquals(limit, phoneDao.findAll("", null, null,0, limit).size());
    }

    @Test
    public void testGetPhoneWithWrongId() {
        long wrongId = 998L;
        assertFalse(phoneDao.get(wrongId).isPresent());
    }

    @Test
    public void testGetPhoneWithRightId() {
        long rightId = 1000L;
        String rightModel = "ARCHOS 101 G9";
        Optional<Phone> phone = phoneDao.get(rightId);
        assertTrue(phone.isPresent());
        assertEquals(rightModel, phone.get().getModel());
    }

    @Test
    public void testInsertPhone() {
        long newId = 10L;
        String newModel = "test_model";
        Set<Color> testColors = new HashSet<>();
        testColors.add(new Color(1000L, "Black"));
        testColors.add(new Color(1002L, "Yellow"));
        testPhone.setColors(testColors);
        testPhone.setId(newId);
        testPhone.setModel(newModel);

        phoneDao.save(testPhone);
        Optional<Phone> phoneFound = phoneDao.get(newId);

        assertTrue(phoneFound.isPresent());
        assertEquals(newModel, phoneFound.get().getModel());
        assertEquals(testColors.size(), phoneFound.get().getColors().size());
    }
}