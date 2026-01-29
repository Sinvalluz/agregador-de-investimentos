package com.sinvaldev.agregadordeinvestimentos.mappers;

import com.sinvaldev.agregadordeinvestimentos.dtos.account.RequestAccountDto;
import com.sinvaldev.agregadordeinvestimentos.dtos.account.ResponseAccountDto;
import com.sinvaldev.agregadordeinvestimentos.model.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account ResponseAccountDtoToAccount (ResponseAccountDto responseAccountDto);

    ResponseAccountDto accountToResponseAccountDto (Account account);
}
