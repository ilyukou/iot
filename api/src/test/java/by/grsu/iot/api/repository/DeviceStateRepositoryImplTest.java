package by.grsu.iot.api.repository;

import by.grsu.iot.api.config.RepositoryTestConfig;
import by.grsu.iot.api.model.dto.thing.device.state.GetStateRequest;
import by.grsu.iot.api.model.dto.thing.device.state.SetDeviceRequest;
import by.grsu.iot.api.repository.sql.DeviceStateRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RepositoryTestConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeviceStateRepositoryImplTest {

    @Autowired
    private DeviceStateRepository deviceStateRepository;

    private GetStateRequest getStateRequest;
    private SetDeviceRequest setDeviceRequest;

    private final String token = "token1";
    private final String state = "on";

    @Before
    public void setUp() {
        getStateRequest = new GetStateRequest(token);
        setDeviceRequest = new SetDeviceRequest(token, state);
    }

    // Wait GetStateRequest
    @Test
    public void crudWithWaitGetStateRequest() {
        Assert.assertFalse(deviceStateRepository.isExistWaitGetStateRequest(token));

        deviceStateRepository.putWaitGetStateRequest(getStateRequest);
        Assert.assertTrue(deviceStateRepository.isExistWaitGetStateRequest(token));

        Assert.assertEquals(getStateRequest, deviceStateRepository.getWaitGetStateRequest(token));

        deviceStateRepository.removeWaitGetStateRequest(token);
        Assert.assertFalse(deviceStateRepository.isExistWaitGetStateRequest(token));
    }

    // Wait SetDeviceRequest
    @Test
    public void crudWithWaitSetDeviceRequest() {
        Assert.assertFalse(deviceStateRepository.isExistWaitSetDeviceRequest(token));

        deviceStateRepository.putWaitSetDeviceRequest(setDeviceRequest);
        Assert.assertTrue(deviceStateRepository.isExistWaitSetDeviceRequest(token));

        Assert.assertEquals(setDeviceRequest, deviceStateRepository.getWaitSetDeviceRequest(token));

        deviceStateRepository.removeWaitSetDeviceRequest(token);
        Assert.assertFalse(deviceStateRepository.isExistWaitSetDeviceRequest(token));
    }

    // Processed GetStateRequest
    @Test
    public void crudWithWaitProcessedRequest() {
        Assert.assertFalse(deviceStateRepository.isExistProcessedSetDeviceRequest(token));

        deviceStateRepository.putSetDeviceRequest(setDeviceRequest);
        Assert.assertTrue(deviceStateRepository.isExistProcessedSetDeviceRequest(token));

        Assert.assertEquals(setDeviceRequest, deviceStateRepository.getProcessedSetDeviceRequest(token));

        deviceStateRepository.removeProcessedSetDeviceRequest(token);
        Assert.assertFalse(deviceStateRepository.isExistProcessedSetDeviceRequest(token));
    }

    // Processed SetDeviceRequest
    @Test
    public void crudWithWaitProcessedDevice() {
        Assert.assertFalse(deviceStateRepository.isExistProcessedGetStateRequest(token));

        deviceStateRepository.putProcessedGetStateRequest(getStateRequest);
        Assert.assertTrue(deviceStateRepository.isExistProcessedGetStateRequest(token));

        Assert.assertEquals(getStateRequest, deviceStateRepository.getProcessedGetStateRequest(token));

        deviceStateRepository.removeProcessedGetStateRequest(token);
        Assert.assertFalse(deviceStateRepository.isExistProcessedGetStateRequest(token));
    }

    @Test
    public void getWaitDeviceAndWaitRequestWithEqualsToken() {
        deviceStateRepository.putProcessedGetStateRequest(getStateRequest);

        Thread myThread = new Thread() {
            public void run() {
                Assert.assertEquals(0L, deviceStateRepository.getWaitGetStateRequestAndWaitSetDeviceRequestWithEqualsToken().size());
            }
        };

        myThread.start();

        deviceStateRepository.putSetDeviceRequest(setDeviceRequest);
    }
}
