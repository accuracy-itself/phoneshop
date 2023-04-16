package com.es.core.model.phone;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class JdbcPhoneDao implements PhoneDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public Optional<Phone> get(final Long key) {
        return jdbcTemplate.query(
                "select phones.*, " +
                        "colors.id as colorId," +
                        "colors.code as colorCode " +
                        "from phones " +
                        "left join phone2color on phone2color.phoneId = phones.id " +
                        "left join colors on phone2color.colorId = colors.id " +
                        "where phones.id = ?",
                new PhoneResultSetExtractor(), key).stream()
                .findAny();
    }

    public void save(final Phone phone) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("phones");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", phone.getId());
        parameters.put("announced", phone.getAnnounced());
        parameters.put("backCameraMegapixels", phone.getBackCameraMegapixels());
        parameters.put("model", phone.getModel());
        parameters.put("brand", phone.getBrand());
        parameters.put("price", phone.getPrice());
        parameters.put("displaySizeInches", phone.getDisplaySizeInches());
        parameters.put("weightGr", phone.getWeightGr());
        parameters.put("lengthMm", phone.getLengthMm());
        parameters.put("widthMm", phone.getWidthMm());
        parameters.put("heightMm", phone.getHeightMm());
        parameters.put("deviceType", phone.getDeviceType());
        parameters.put("os", phone.getOs());
        parameters.put("displayResolution", phone.getDisplayResolution());
        parameters.put("pixelDensity", phone.getPixelDensity());
        parameters.put("displayTechnology", phone.getDisplayTechnology());
        parameters.put("frontCameraMegapixels", phone.getFrontCameraMegapixels());
        parameters.put("ramGb", phone.getRamGb());
        parameters.put("internalStorageGb", phone.getInternalStorageGb());
        parameters.put("batteryCapacityMah", phone.getBatteryCapacityMah());
        parameters.put("talkTimeHours", phone.getId());
        parameters.put("standByTimeHours", phone.getStandByTimeHours());
        parameters.put("bluetooth", phone.getBluetooth());
        parameters.put("positioning", phone.getPositioning());
        parameters.put("imageUrl", phone.getImageUrl());
        simpleJdbcInsert.execute(parameters);
    }

    public List<Phone> findAll(int offset, int limit) {
        return jdbcTemplate.query(
                "select phones.*, " +
                        "colors.id as colorId," +
                        "colors.code as colorCode " +
                        "from (select * from phones offset " + offset + " limit " + limit + ") as phones " +
                        "left join phone2color on phone2color.phoneId = phones.id " +
                        "left join colors on phone2color.colorId = colors.id",
                new PhoneResultSetExtractor());
    }
}
