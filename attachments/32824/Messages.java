package jenkins.plugins.publish_over_ssh;

import org.jvnet.localizer.Localizable;
import org.jvnet.localizer.ResourceBundleHolder;

public class Messages {
    private static final ResourceBundleHolder holder = ResourceBundleHolder.get(Messages.class);

    public Messages() {
    }

    public static String console_exec_connected() {
        return holder.format("console.exec.connected", new Object[0]);
    }

    public static Localizable _console_exec_connected() {
        return new Localizable(holder, "console.exec.connected", new Object[0]);
    }

    public static String console_put(Object arg0) {
        return holder.format("console.put", new Object[]{arg0});
    }

    public static Localizable _console_put(Object arg0) {
        return new Localizable(holder, "console.put", new Object[]{arg0});
    }

    public static String exception_exec_timeout(Object arg0) {
        return holder.format("exception.exec.timeout", new Object[]{arg0});
    }

    public static Localizable _exception_exec_timeout(Object arg0) {
        return new Localizable(holder, "exception.exec.timeout", new Object[]{arg0});
    }

    public static String console_usingPwd() {
        return holder.format("console.usingPwd", new Object[0]);
    }

    public static Localizable _console_usingPwd() {
        return new Localizable(holder, "console.usingPwd", new Object[0]);
    }

    public static String postBuild_descriptor_displayName() {
        return holder.format("postBuild.descriptor.displayName", new Object[0]);
    }

    public static Localizable _postBuild_descriptor_displayName() {
        return new Localizable(holder, "postBuild.descriptor.displayName", new Object[0]);
    }

    public static String descriptor_testConnection_error() {
        return holder.format("descriptor.testConnection.error", new Object[0]);
    }

    public static Localizable _descriptor_testConnection_error() {
        return new Localizable(holder, "descriptor.testConnection.error", new Object[0]);
    }

    public static String exception_badTransferConfig() {
        return holder.format("exception.badTransferConfig", new Object[0]);
    }

    public static Localizable _exception_badTransferConfig() {
        return new Localizable(holder, "exception.badTransferConfig", new Object[0]);
    }

    public static String exception_disconnect_session(Object arg0) {
        return holder.format("exception.disconnect.session", new Object[]{arg0});
    }

    public static Localizable _exception_disconnect_session(Object arg0) {
        return new Localizable(holder, "exception.disconnect.session", new Object[]{arg0});
    }

    public static String console_failure(Object arg0) {
        return holder.format("console.failure", new Object[]{arg0});
    }

    public static Localizable _console_failure(Object arg0) {
        return new Localizable(holder, "console.failure", new Object[]{arg0});
    }

    public static String console_cd(Object arg0) {
        return holder.format("console.cd", new Object[]{arg0});
    }

    public static Localizable _console_cd(Object arg0) {
        return new Localizable(holder, "console.cd", new Object[]{arg0});
    }

    public static String exception_exec_exitStatus(Object arg0) {
        return holder.format("exception.exec.exitStatus", new Object[]{arg0});
    }

    public static Localizable _exception_exec_exitStatus(Object arg0) {
        return new Localizable(holder, "exception.exec.exitStatus", new Object[]{arg0});
    }

    public static String global_hostconfig_descriptor() {
        return holder.format("global.hostconfig.descriptor", new Object[0]);
    }

    public static Localizable _global_hostconfig_descriptor() {
        return new Localizable(holder, "global.hostconfig.descriptor", new Object[0]);
    }

    public static String console_sftp_opened() {
        return holder.format("console.sftp.opened", new Object[0]);
    }

    public static Localizable _console_sftp_opened() {
        return new Localizable(holder, "console.sftp.opened", new Object[0]);
    }

    public static String exception_failedToCreateClient(Object arg0) {
        return holder.format("exception.failedToCreateClient", new Object[]{arg0});
    }

    public static Localizable _exception_failedToCreateClient(Object arg0) {
        return new Localizable(holder, "exception.failedToCreateClient", new Object[]{arg0});
    }

    public static String exception_cwdException(Object arg0, Object arg1) {
        return holder.format("exception.cwdException", new Object[]{arg0, arg1});
    }

    public static Localizable _exception_cwdException(Object arg0, Object arg1) {
        return new Localizable(holder, "exception.cwdException", new Object[]{arg0, arg1});
    }

    public static String exception_session_connect(Object arg0, Object arg1) {
        return holder.format("exception.session.connect", new Object[]{arg0, arg1});
    }

    public static Localizable _exception_session_connect(Object arg0, Object arg1) {
        return new Localizable(holder, "exception.session.connect", new Object[]{arg0, arg1});
    }

    public static String console_sftp_connected() {
        return holder.format("console.sftp.connected", new Object[0]);
    }

    public static Localizable _console_sftp_connected() {
        return new Localizable(holder, "console.sftp.connected", new Object[0]);
    }

    public static String log_sftp_stat(Object arg0, Object arg1) {
        return holder.format("log.sftp.stat", new Object[]{arg0, arg1});
    }

    public static Localizable _log_sftp_stat(Object arg0, Object arg1) {
        return new Localizable(holder, "log.sftp.stat", new Object[]{arg0, arg1});
    }

    public static String console_exec_opened() {
        return holder.format("console.exec.opened", new Object[0]);
    }

    public static Localizable _console_exec_opened() {
        return new Localizable(holder, "console.exec.opened", new Object[0]);
    }

    public static String exception_addIdentity(Object arg0) {
        return holder.format("exception.addIdentity", new Object[]{arg0});
    }

    public static Localizable _exception_addIdentity(Object arg0) {
        return new Localizable(holder, "exception.addIdentity", new Object[]{arg0});
    }

    public static String publisher_descriptor_displayName() {
        return holder.format("publisher.descriptor.displayName", new Object[0]);
    }

    public static Localizable _publisher_descriptor_displayName() {
        return new Localizable(holder, "publisher.descriptor.displayName", new Object[0]);
    }

    public static String console_exec_opening() {
        return holder.format("console.exec.opening", new Object[0]);
    }

    public static Localizable _console_exec_opening() {
        return new Localizable(holder, "console.exec.opening", new Object[0]);
    }

    public static String builder_descriptor_displayName() {
        return holder.format("builder.descriptor.displayName", new Object[0]);
    }

    public static Localizable _builder_descriptor_displayName() {
        return new Localizable(holder, "builder.descriptor.displayName", new Object[0]);
    }

    public static String descriptor_sourceFiles_check_configNotFound(Object arg0) {
        return holder.format("descriptor.sourceFiles.check.configNotFound", new Object[]{arg0});
    }

    public static Localizable _descriptor_sourceFiles_check_configNotFound(Object arg0) {
        return new Localizable(holder, "descriptor.sourceFiles.check.configNotFound", new Object[]{arg0});
    }

    public static String exception_exec_connect(Object arg0) {
        return holder.format("exception.exec.connect", new Object[]{arg0});
    }

    public static Localizable _exception_exec_connect(Object arg0) {
        return new Localizable(holder, "exception.exec.connect", new Object[]{arg0});
    }

    public static String descriptor_testConnection_ok() {
        return holder.format("descriptor.testConnection.ok", new Object[0]);
    }

    public static Localizable _descriptor_testConnection_ok() {
        return new Localizable(holder, "descriptor.testConnection.ok", new Object[0]);
    }

    public static String console_sftp_connecting() {
        return holder.format("console.sftp.connecting", new Object[0]);
    }

    public static Localizable _console_sftp_connecting() {
        return new Localizable(holder, "console.sftp.connecting", new Object[0]);
    }

    public static String console_exec_connecting(Object arg0) {
        return holder.format("console.exec.connecting", new Object[]{arg0});
    }

    public static Localizable _console_exec_connecting(Object arg0) {
        return new Localizable(holder, "console.exec.connecting", new Object[]{arg0});
    }

    public static String exception_exec_open(Object arg0) {
        return holder.format("exception.exec.open", new Object[]{arg0});
    }

    public static Localizable _exception_exec_open(Object arg0) {
        return new Localizable(holder, "exception.exec.open", new Object[]{arg0});
    }

    public static String descriptor_sourceOrExec() {
        return holder.format("descriptor.sourceOrExec", new Object[0]);
    }

    public static Localizable _descriptor_sourceOrExec() {
        return new Localizable(holder, "descriptor.sourceOrExec", new Object[0]);
    }

    public static String exception_disconnect_sftp(Object arg0) {
        return holder.format("exception.disconnect.sftp", new Object[]{arg0});
    }

    public static Localizable _exception_disconnect_sftp(Object arg0) {
        return new Localizable(holder, "exception.disconnect.sftp", new Object[]{arg0});
    }

    public static String exception_pwdNotAbsolute(Object arg0) {
        return holder.format("exception.pwdNotAbsolute", new Object[]{arg0});
    }

    public static Localizable _exception_pwdNotAbsolute(Object arg0) {
        return new Localizable(holder, "exception.pwdNotAbsolute", new Object[]{arg0});
    }

    public static String global_common_descriptor() {
        return holder.format("global.common.descriptor", new Object[0]);
    }

    public static Localizable _global_common_descriptor() {
        return new Localizable(holder, "global.common.descriptor", new Object[0]);
    }

    public static String console_session_connecting() {
        return holder.format("console.session.connecting", new Object[0]);
    }

    public static Localizable _console_session_connecting() {
        return new Localizable(holder, "console.session.connecting", new Object[0]);
    }

    public static String console_message_prefix() {
        return holder.format("console.message.prefix", new Object[0]);
    }

    public static Localizable _console_message_prefix() {
        return new Localizable(holder, "console.message.prefix", new Object[0]);
    }

    public static String retry_descriptor_displayName() {
        return holder.format("retry.descriptor.displayName", new Object[0]);
    }

    public static Localizable _retry_descriptor_displayName() {
        return new Localizable(holder, "retry.descriptor.displayName", new Object[0]);
    }

    public static String promotion_descriptor_displayName() {
        return holder.format("promotion.descriptor.displayName", new Object[0]);
    }

    public static Localizable _promotion_descriptor_displayName() {
        return new Localizable(holder, "promotion.descriptor.displayName", new Object[0]);
    }

    public static String transfer_descriptor_displayName() {
        return holder.format("transfer.descriptor.displayName", new Object[0]);
    }

    public static Localizable _transfer_descriptor_displayName() {
        return new Localizable(holder, "transfer.descriptor.displayName", new Object[0]);
    }

    public static String console_sftp_opening() {
        return holder.format("console.sftp.opening", new Object[0]);
    }

    public static Localizable _console_sftp_opening() {
        return new Localizable(holder, "console.sftp.opening", new Object[0]);
    }

    public static String console_success() {
        return holder.format("console.success", new Object[0]);
    }

    public static Localizable _console_success() {
        return new Localizable(holder, "console.success", new Object[0]);
    }

    public static String exception_sftp_connect(Object arg0) {
        return holder.format("exception.sftp.connect", new Object[]{arg0});
    }

    public static Localizable _exception_sftp_connect(Object arg0) {
        return new Localizable(holder, "exception.sftp.connect", new Object[]{arg0});
    }

    public static String console_mkdir(Object arg0) {
        return holder.format("console.mkdir", new Object[]{arg0});
    }

    public static Localizable _console_mkdir(Object arg0) {
        return new Localizable(holder, "console.mkdir", new Object[]{arg0});
    }

    public static String exception_pwd(Object arg0) {
        return holder.format("exception.pwd", new Object[]{arg0});
    }

    public static Localizable _exception_pwd(Object arg0) {
        return new Localizable(holder, "exception.pwd", new Object[]{arg0});
    }

    public static String publisherLabel_descriptor_displayName() {
        return holder.format("publisherLabel.descriptor.displayName", new Object[0]);
    }

    public static Localizable _publisherLabel_descriptor_displayName() {
        return new Localizable(holder, "publisherLabel.descriptor.displayName", new Object[0]);
    }

    public static String console_userInfo_returning(Object arg0) {
        return holder.format("console.userInfo.returning", new Object[]{arg0});
    }

    public static Localizable _console_userInfo_returning(Object arg0) {
        return new Localizable(holder, "console.userInfo.returning", new Object[]{arg0});
    }

    public static String preBuild_descriptor_displayName() {
        return holder.format("preBuild.descriptor.displayName", new Object[0]);
    }

    public static Localizable _preBuild_descriptor_displayName() {
        return new Localizable(holder, "preBuild.descriptor.displayName", new Object[0]);
    }

    public static String exception_disconnect_exec(Object arg0) {
        return holder.format("exception.disconnect.exec", new Object[]{arg0});
    }

    public static Localizable _exception_disconnect_exec(Object arg0) {
        return new Localizable(holder, "exception.disconnect.exec", new Object[]{arg0});
    }

    public static String paramPublish_descriptor_displayName() {
        return holder.format("paramPublish.descriptor.displayName", new Object[0]);
    }

    public static Localizable _paramPublish_descriptor_displayName() {
        return new Localizable(holder, "paramPublish.descriptor.displayName", new Object[0]);
    }

    public static String descriptor_testConnection_sftpError() {
        return holder.format("descriptor.testConnection.sftpError", new Object[0]);
    }

    public static Localizable _descriptor_testConnection_sftpError() {
        return new Localizable(holder, "descriptor.testConnection.sftpError", new Object[0]);
    }

    public static String descriptor_displayName() {
        return holder.format("descriptor.displayName", new Object[0]);
    }

    public static Localizable _descriptor_displayName() {
        return new Localizable(holder, "descriptor.displayName", new Object[0]);
    }

    public static String console_exec_completed(Object arg0) {
        return holder.format("console.exec.completed", new Object[]{arg0});
    }

    public static Localizable _console_exec_completed(Object arg0) {
        return new Localizable(holder, "console.exec.completed", new Object[]{arg0});
    }

    public static String console_session_creating(Object arg0, Object arg1, Object arg2) {
        return holder.format("console.session.creating", new Object[]{arg0, arg1, arg2});
    }

    public static Localizable _console_session_creating(Object arg0, Object arg1, Object arg2) {
        return new Localizable(holder, "console.session.creating", new Object[]{arg0, arg1, arg2});
    }

    public static String console_session_connected() {
        return holder.format("console.session.connected", new Object[0]);
    }

    public static Localizable _console_session_connected() {
        return new Localizable(holder, "console.session.connected", new Object[0]);
    }

    public static String exception_session_create(Object arg0, Object arg1, Object arg2, Object arg3) {
        return holder.format("exception.session.create", new Object[]{arg0, arg1, arg2, arg3});
    }

    public static Localizable _exception_session_create(Object arg0, Object arg1, Object arg2, Object arg3) {
        return new Localizable(holder, "exception.session.create", new Object[]{arg0, arg1, arg2, arg3});
    }

    public static String exception_sftp_open(Object arg0) {
        return holder.format("exception.sftp.open", new Object[]{arg0});
    }

    public static Localizable _exception_sftp_open(Object arg0) {
        return new Localizable(holder, "exception.sftp.open", new Object[]{arg0});
    }

    public static String exception_badTransferConfig_noExec() {
        return holder.format("exception.badTransferConfig.noExec", new Object[0]);
    }

    public static Localizable _exception_badTransferConfig_noExec() {
        return new Localizable(holder, "exception.badTransferConfig.noExec", new Object[0]);
    }
}