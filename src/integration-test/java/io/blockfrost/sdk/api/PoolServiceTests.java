package io.blockfrost.sdk.api;

import io.blockfrost.sdk.api.exception.APIException;
import io.blockfrost.sdk.api.util.Constants;
import io.blockfrost.sdk.api.util.OrderEnum;
import io.blockfrost.sdk.impl.PoolServiceImpl;
import io.blockfrost.sdk.impl.helper.ValidationHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PoolServiceTests extends TestBase {

    PoolService poolService;

    @BeforeEach
    public void setup(){
        poolService = new PoolServiceImpl(Constants.BLOCKFROST_TESTNET_URL, projectId);
    }

    @Test
    public void pools_willReturn_poolsForCountPageAndAscendingOrder() throws APIException {

        String[] expectedPoolList = {
                "pool1adur9jcn0dkjpm3v8ayf94yn3fe5xfk2rqfz7rfpuh6cw6evd7w",
                "pool18kd2k7kqt9gje9y0azahww4dqak9azeeg8ayl0xl7dzewg70vlf",
                "pool13dgxp4ph2ut5datuh5na4wy7hrnqgkj4fyvac3e8fzfqcc7qh0h",
                "pool1wnf793xkgrw3s800tfdkkg3s3ddgxkucenahzs7490g4q0cpe0v",
                "pool156gxlrk0e3phxadasa33yzk9e94wg7tv3au02jge8eanv9zc4ym"
        };

        List<String> poolList = poolService.getPools(5, 1, OrderEnum.asc);

        assertThat(poolList, hasSize(5));
        assertThat(poolList, contains(expectedPoolList));

    }

    @Test
    public void pools_willReturn_poolsForCountPage() throws APIException {

        String[] expectedPoolList = {
                "pool1adur9jcn0dkjpm3v8ayf94yn3fe5xfk2rqfz7rfpuh6cw6evd7w",
                "pool18kd2k7kqt9gje9y0azahww4dqak9azeeg8ayl0xl7dzewg70vlf",
                "pool13dgxp4ph2ut5datuh5na4wy7hrnqgkj4fyvac3e8fzfqcc7qh0h",
                "pool1wnf793xkgrw3s800tfdkkg3s3ddgxkucenahzs7490g4q0cpe0v",
                "pool156gxlrk0e3phxadasa33yzk9e94wg7tv3au02jge8eanv9zc4ym"
        };

        List<String> poolList = poolService.getPools(5, 1);

        assertThat(poolList, hasSize(5));
        assertThat(poolList, contains(expectedPoolList));

    }

    @Test
    public void pools_willThrowAPIException_onCountGreaterThan100() {

        Exception exception = assertThrows( APIException.class, () -> poolService.getPools(101, 1 ));
        assertThat(exception.getMessage(), containsString(ValidationHelper.COUNT_VALIDATION_MESSAGE));

    }

}
